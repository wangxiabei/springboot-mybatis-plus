package com.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.bean.Admin;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    Admin getAdminByName(@Param("phone") String loginName);

    List<String> getAdminAuthList(Long adminId);
}
