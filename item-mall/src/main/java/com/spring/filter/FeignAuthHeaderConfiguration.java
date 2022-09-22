package com.spring.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

public class FeignAuthHeaderConfiguration {
    @Bean
    public RequestContextListener requestContextListenerBean() {
        return new RequestContextListener();
    }

    @Bean
    public FeignInterceptor feignInterceptor() {
        return new FeignInterceptor();
    }
}
