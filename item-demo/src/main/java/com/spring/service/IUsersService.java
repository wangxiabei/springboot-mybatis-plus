package com.spring.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.model.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bei_ch
 * @since 2022-02-17
 */
public interface IUsersService extends IService<Users> {

    Page<Users> list(Integer pageSize, Integer pageNum);

}
