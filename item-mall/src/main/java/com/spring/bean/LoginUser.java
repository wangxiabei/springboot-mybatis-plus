package com.spring.bean;

import lombok.Data;

import java.util.List;

@Data
public class LoginUser {
    private String id;
    private String nickname;
    private String name;
    private String password;
    private String salt;
    private String phone;
    private String Nonce;
    private List<String> AuthList;
}
