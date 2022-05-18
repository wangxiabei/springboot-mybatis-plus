package com.spring.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.model.auto.MallMenus;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-01-13
 */
public interface IMallMenusService extends IService<MallMenus> {


    boolean createMenu(MallMenus mallMenus);


    Page<MallMenus> list(Long parentId, Integer pageSize, Integer pageNum);


    Page<MallMenus> getList(Integer pageSize, Integer pageNum);

    boolean deleteMenu(Long id);

    boolean updateMenu(Long id, MallMenus mallMenus);

}
