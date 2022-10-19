package com.spring.filter;

import com.alibaba.fastjson.JSON;
import com.spring.bean.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**（它能够确保在一次请求中只通过一次filter，而需要重复的执行）
 * 将登录用户的TOKEN转化成用户信息的全局过滤器
 * 当鉴权通过后将令牌中的用户信息解析出来，然后存入请求的Header中，这样后续服务就不需要解析令牌了，可以直接从请求的Header中获取到用户信息。
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    @Autowired
    TokenStore tokenStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        log.info("前置: " +authHeader);
        log.info("tokenValue: " +authHeader.get(0));
        String token = null;
        if (authHeader != null) {
            String tokenValue = authHeader.get(0);
            tokenValue = tokenValue.replace("Bearer ", "").trim();
            log.info("tokenValue: " +tokenValue);
            token = buildToken(tokenValue);
            log.info("token: " +token);
        }
        if (token != null) {
            // 定义新的消息头
            HttpHeaders headers = new HttpHeaders();
            headers.put("json-token", Collections.singletonList(token));
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove("Authorization");
            ServerHttpRequest host = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    return headers;
                }
            };
            //将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build).then(Mono.fromRunnable(() -> {
            }));
        } else {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            }));
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * redis token 转发明文给微服务
     *
     * @return
     */
    private String buildToken(String tokenValue) {

        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(tokenValue);
        log.info("buildToken:"+auth2Authentication);
        if (auth2Authentication == null) {
            return null;
        }
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        Authentication userAuthentication = auth2Authentication.getUserAuthentication();
        String userInfoStr = clientId;
        List<String> authorities = new ArrayList<>();
        if (userAuthentication != null) {
            LoginUser loginUser = (LoginUser) userAuthentication.getPrincipal();
            log.info("前置: " +loginUser);
            userInfoStr = JSON.toJSONString(loginUser);
            // 组装明文token，转发给微服务，放入header，名称为json-token
            userAuthentication.getAuthorities().stream().forEach(
                    s -> authorities.add(((GrantedAuthority) s).getAuthority())
            );
        }

        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        Map<String, String> requestParams = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonToken = new HashMap<>(requestParams);
        jsonToken.put("principal", userInfoStr);
        jsonToken.put("authorities", authorities);

        log.info("前置: " +jsonToken);

        return Base64.getEncoder().encodeToString(JSON.toJSONString(jsonToken).getBytes(StandardCharsets.UTF_8));
    }
}
