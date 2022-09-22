package com.spring.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring.bean.LoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;

import java.util.Collections;

public class LoginUserUtils {
    public LoginUserUtils(){

    }

    public static LoginUser getLoginUser() throws Exception {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("json-token");
        System.out.println("token"+token);
        String json = new String(Base64.decodeBase64(token));
        System.out.println("json"+json);
        JSONObject userJson = JSON.parseObject(json);
        System.out.println("userJson"+userJson);
        if (!"client_credentials".equals(userJson.getString("grant_type"))) {
            String principal = userJson.getString("principal");
            LoginUser loginUser = new LoginUser();
            loginUser.setName(userJson.getString("name"));
            loginUser.setPhone(userJson.getString("phone"));
            loginUser.setPassword(userJson.getString("password"));
            loginUser.setNickname(userJson.getString("nickname"));
            loginUser.setNonce(userJson.getString("nonce"));
            loginUser.setId(userJson.getString("id"));
            loginUser.setAuthList(Collections.singletonList(userJson.getString("authorities")));
//                    (LoginUser)JSON.parseObject(principal, LoginUser.class);
            return loginUser;
        } else {
            return null;
        }
    }
}
