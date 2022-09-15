package com.spring.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
public class MyOAuth2AccessToken extends DefaultOAuth2AccessToken {

    public MyOAuth2AccessToken(String value) {
        super(value);
    }
    public MyOAuth2AccessToken(OAuth2AccessToken token) {
        super(token);
    }
}
