package com.spring.controller;

import com.spring.config.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class Oauth2TokenController {
    @Autowired
    TokenEndpoint tokenEndpoint;

    @GetMapping("/token")
    public CommonResult getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        System.out.println("Get的请求方式");
        return custom(tokenEndpoint.getAccessToken(principal, parameters).getBody());
    }

    @PostMapping("/token")
    public CommonResult postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        System.out.println("post的请求方式");
        return custom(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    /**
     * 定制申请返回实体
     * @param accessToken
     * @return
     */
    private CommonResult custom(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        CommonResult result = new CommonResult();
        result.setData(token);
        return result;
    }

}
