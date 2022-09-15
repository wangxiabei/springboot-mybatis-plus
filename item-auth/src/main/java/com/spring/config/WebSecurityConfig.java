package com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * springsecurity的安全配置
 * 安全配置要优先于资源服务器
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 安全验证机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();// 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable().cacheControl();
        http.requestMatchers()
                .antMatchers("/oauth/**","/auth/login","/authentication/base","/logout")
                .and()
                .authorizeRequests()
                .antMatchers("/login*","/auth/login","/auth/logout")
                .permitAll()//不验证，直接放行
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()//允许表单登录
                .loginPage("/auth/login")//登录页面地址
                .loginProcessingUrl("/authentication/base");//登录处理的url

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //自定义的md5密码验证器
        return new MyMd5Password();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }
}
