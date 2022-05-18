package com.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.model.Users;
import com.spring.mapper.UsersMapper;
import com.spring.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bei_ch
 * @since 2022-02-17
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public Page<Users> list(Integer pageSize, Integer pageNum) {
        Page<Users> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Users> wrapper = new QueryWrapper();
        wrapper.lambda().isNull(Users::getDeletedAt);
        return (Page<Users>) page(page,wrapper);
    }
}
