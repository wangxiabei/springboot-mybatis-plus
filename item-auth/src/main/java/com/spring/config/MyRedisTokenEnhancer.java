package com.spring.config;

import com.alibaba.fastjson.JSON;
import com.spring.bean.LoginUser;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Component
public class MyRedisTokenEnhancer implements TokenEnhancer {
    private static final Random RANDOM = new SecureRandom();

    @SneakyThrows
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        String newToken = accessToken.getValue();
        if(authentication.getUserAuthentication() != null){
            //密码模式，简化模式，授权码模式（需要走用户密码认证的模式）
            //生成一个随机字符串，保证每次token不一样
            ((LoginUser)authentication.getPrincipal()).setNonce(getNonce());
            String loginUserJsonStr = JSON.toJSONString(authentication.getPrincipal());
            newToken = Base64.getUrlEncoder().encodeToString(loginUserJsonStr.getBytes(StandardCharsets.UTF_8));
        }
        defaultOAuth2AccessToken.setValue(newToken);

        OAuth2Request request = authentication.getOAuth2Request();
        //密码模式清除验证码
        if("password".equals(request.getGrantType())){
            //String captchaKey = request.getRequestParameters().get("captcha_key");
            //captchaRedis.delete(captchaKey);
        }
        //清除客户端缓存
        String clientId = request.getClientId();
       // baseClientDetailsRedis.delete(clientId);
        //客户端缓存不过期
        //clientDetailsRedis.delete(clientId);
        //return accessToken;
        return new MyOAuth2AccessToken(defaultOAuth2AccessToken);
        //return defaultOAuth2AccessToken;
    }
    public static String getNonce() {
        char[] nonceChars = new char[32];

        for(int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(RANDOM.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length()));
        }

        return new String(nonceChars);
    }
}
