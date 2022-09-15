package com.spring.filter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * 解析从网关发送的token，权限配置
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("json-token");
        if (token !=null){
            String json = new String(Base64.getDecoder().decode(token));
            JSONObject userJson = JSON.parseObject(json);
            //客户端模式没经过用户名密码登录，所以得不到LoginUser信息
            if (!"client_credentials".equals(userJson.getString("grant_type"))){
                String principal = userJson.getString("principal");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
