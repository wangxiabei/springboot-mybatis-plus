package com.spring.config;

import com.spring.auth.MyDefaultAuthenticationKeyGenerator;
import com.spring.auth.MyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * token存储方式(令牌的配置)
 */
@Configuration
public class TokenConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore tokenStore(){
        //使用redisTokenStore生成令牌
        MyRedisTokenStore store = new MyRedisTokenStore(redisConnectionFactory);
        store.setAuthenticationKeyGenerator(new MyDefaultAuthenticationKeyGenerator());
        return store;
    }
}
