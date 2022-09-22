package com.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Admin{
    private Long id;
    private String name;
    private String password;
    private String phone;
}
