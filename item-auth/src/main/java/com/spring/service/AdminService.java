package com.spring.service;

import com.spring.bean.Admin;

import java.util.List;

public interface AdminService {
    Admin getUserByName(String name);
    List<String> getAuthList(Long adminId);
}
