package com.spring.auth;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class MyException extends OAuth2Exception {
    public MyException(String msg, Throwable t) {
        super(msg, t);
    }
    public MyException(String masg){
        super(masg);
    }
}
