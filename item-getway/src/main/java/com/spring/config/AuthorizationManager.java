package com.spring.config;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 鉴权管理器.用于判断是否有资源的访问权限
 */
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final String RESOURCE_ID= "admin";
    TokenStore tokenStore;
    private List<String> scopes = new ArrayList<>();
    public AuthorizationManager(TokenStore tokenStore, String scope, String... scopes){
        this.tokenStore = tokenStore;
        this.scopes.add(scope);
        if (this.scopes != null){
            for (String s: scopes){
                this.scopes.add(s);
            }
        }
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        List<String> authHeader = authorizationContext.getExchange().getRequest().getHeaders().get("Authorization");
        if (authHeader == null){
            throw new RuntimeException("认证异常请重新登录");
        }
        String token = authHeader.get(0);
        if (StringUtils.isBlank(token)){
            throw new RuntimeException("认证异常请重新登录");
        }
        token = token.replace("Bearer ","").trim();
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
        if (oAuth2Authentication == null){
            throw  new RuntimeException("认证异常请重新登录");
        }
        Collection<String> resourceIds = oAuth2Authentication.getOAuth2Request().getResourceIds();
        if (resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(RESOURCE_ID)){
            throw new RuntimeException("权限不足");
        }
        Set<String> clientScopes = oAuth2Authentication.getOAuth2Request().getScope();
        for (String scope: this.scopes){
            if (clientScopes.contains(scope)){
                return Mono.just(new AuthorizationDecision(true));
            }
        }
       throw new RuntimeException("权限不足");
    }
}
