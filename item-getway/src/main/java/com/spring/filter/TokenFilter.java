package com.spring.filter;

import com.alibaba.fastjson.JSON;
import com.spring.bean.LoginUser;
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
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**（它能够确保在一次请求中只通过一次filter，而需要重复的执行）
 * 将登录用户的TOKEN转化成用户信息的全局过滤器
 * 当鉴权通过后将令牌中的用户信息解析出来，然后存入请求的Header中，这样后续服务就不需要解析令牌了，可以直接从请求的Header中获取到用户信息。
 */
public class TokenFilter implements GlobalFilter, Ordered {
    @Autowired
    TokenStore tokenStore;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        String token = null;
        if (authHeader != null){
            String tokenVaule = authHeader.get(0);
            tokenVaule = tokenVaule.replace("bearer","").trim();
            token = buildToken(tokenVaule);
        }
        if (token != null){
            HttpHeaders headers = new HttpHeaders();
            headers.put("json-token",Collections.singletonList(token));
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove("Authorization");
            ServerHttpRequest host = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders(){
                    return headers;
                }
            };
            //将现在的request变成chanage对象
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build).then(Mono.fromRunnable(() -> {

            }));
        }else {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {}));
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     *
     * @param tokenVaule
     * @return
     */
    private String buildToken(String tokenVaule){
        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(tokenVaule);
        if (auth2Authentication == null){
            return null;
        }
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        Authentication userAuthentication = auth2Authentication.getUserAuthentication();
        String userInfoStr = clientId;
        List<String> authorities = new ArrayList<>();
        if (userAuthentication != null){
            LoginUser loginUser = (LoginUser)userAuthentication.getPrincipal();
            userInfoStr = JSON.toJSONString(loginUser);
            userAuthentication.getAuthorities().stream().forEach(
                    s -> authorities.add(((GrantedAuthority) s).getAuthority())
            );
        }
        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        Map<String,String> requsetParams = oAuth2Request.getRequestParameters();
        Map<String,Object> jsonToken = new HashMap<>();
        jsonToken.put("principal",userInfoStr);
        jsonToken.put("authorities",authorities);
        return Base64.getEncoder().encodeToString(JSON.toJSONString(jsonToken).getBytes(StandardCharsets.UTF_8));
    }
}
