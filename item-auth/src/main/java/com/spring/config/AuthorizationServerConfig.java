package com.spring.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MyRedisTokenEnhancer myRedisTokenEnhancer;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisConnectionFactory connectionFactory;

    /**
     * 令牌访问断点的安全策略
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("permitAll()");//oauth/token_key公开
        security.checkTokenAccess("permitAll()");//oauth/check_token公开;
    }

    /**
     * 客户端信息
     *
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsServices() {
        MyClientDetailsService detailsService = new MyClientDetailsService(redisTemplate,connectionFactory);
        System.out.println("detailsService"+detailsService);
        return detailsService;
    }

    /**
     * 客户端信息配置（从数据库读取）
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsServices());
//        clients.inMemory()
//                //客户端名称
//                .withClient("test-pc")
//                //客户端密码
//                .secret(passwordEncoder.encode("123456"))
//                //资源id,商品资源
//                .resourceIds("oauth2-server")
//                //授权类型, 可同时支持多种授权类型
//                .authorizedGrantTypes("authorization_code", "password", "implicit","client_credentials","refresh_token")
//                //授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
//                .scopes("all")
//                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码
//                .autoApprove(true)
//                .redirectUris("http://www.baidu.com/");//客户端回调地址
    }


    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        MyDefaultTokenServices services = new MyDefaultTokenServices();
        services.setClientDetailsService(clientDetailsServices());//客户端详情
        services.setSupportRefreshToken(true);
        services.setReuseRefreshToken(false);
        services.setTokenStore(tokenStore);

        // token增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List enhancer = new ArrayList<>();
        enhancer.add(myRedisTokenEnhancer);
        tokenEnhancerChain.setTokenEnhancers(enhancer);
        services.setTokenEnhancer(tokenEnhancerChain);

        services.setAccessTokenValiditySeconds(2 * 60 * 60);
        services.setRefreshTokenValiditySeconds(1 * 24 * 60 * 60);
        return services;
    }

    /**
     * 令牌访问断点配置
     * /oauth/authorize 授权端点
     * /oauth/token 令牌端点
     * /oauth/confirm_access 用户确认授权提交端点
     * /oauth/error 授权服务错误信息端点
     * /oauth/check_token 资源服务访问的令牌解析端点
     * /oauth/token_key 提供公有密钥的端点，如果你使用的是JWT令牌的话
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) //密码模式所需要的authenticationManager
                .tokenServices(tokenServices())  //令牌管理服务，无论那种模式都需要
                .allowedTokenEndpointRequestMethods(HttpMethod.POST) //只允许post提交访问令牌
        ;
        endpoints.pathMapping("/oauth/confirm_access", "/auth/confirm_access");
    }
}
