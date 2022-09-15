package com.spring.config;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class ClientDetailThreadLocal {
    public static final ThreadLocal<BaseClientDetails> VALUE = new ThreadLocal<BaseClientDetails>();
}
