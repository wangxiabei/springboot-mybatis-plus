package com.spring.config;

import com.spring.exception.RestfulAccessDeniedHandler;
import com.spring.exception.RestfulAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 *资源服务器配置
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    TokenStore tokenStore;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.authorizeExchange().pathMatchers("/uaa*/*").permitAll()
                .pathMatchers("/admin*/api/**").access(new AuthorizationManager(tokenStore,"API_ADMIN"))
                .pathMatchers("/mall/**").permitAll()
                .anyExchange().permitAll()
                .and().exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler) //处理未授权
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)//处理未认证
                .and().csrf().disable();
        return http.build();
    }

}
