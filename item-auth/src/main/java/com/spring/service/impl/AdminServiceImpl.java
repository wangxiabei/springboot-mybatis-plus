package com.spring.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.bean.Admin;
import com.spring.mapper.AdminMapper;
import com.spring.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin getUserByName(String name) {
        System.out.println("wqeqweqwewq");
        return this.baseMapper.getAdminByName(name);
    }

    @Override
    public List<String> getAuthList(Long adminId) {
        return this.baseMapper.getAdminAuthList(adminId);
    }
}
