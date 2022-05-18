package com.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.model.auto.MallMenus;
import com.spring.mapper.auto.MallMenusMapper;
import com.spring.service.IMallMenusService;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-01-13
 */
@Service
public class MallMenusServiceImpl extends ServiceImpl<MallMenusMapper, MallMenus> implements IMallMenusService {

    @Override
    public boolean createMenu(MallMenus mallMenus) {
        mallMenus.setCreatedAt(new Date());
        mallMenus.setUpdatedAt(new Date());
        return save(mallMenus);
    }

    @Override
    public Page<MallMenus> list(Long parentId, Integer pageSize, Integer pageNum) {
        Page<MallMenus> page = new Page<>(pageNum,pageSize);
        QueryWrapper<MallMenus> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MallMenus::getParentId,parentId)
                .orderByDesc(MallMenus::getId);
        return (Page<MallMenus>) page(page,wrapper);
    }

    @Override
    public Page<MallMenus> getList(Integer pageSize, Integer pageNum) {
        Page<MallMenus> page = new Page<>(pageNum,pageSize);
        QueryWrapper<MallMenus> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .isNull(MallMenus::getDeletedAt)
                .orderByDesc(MallMenus::getPosition);
        return (Page<MallMenus>) page(page,wrapper);
    }

    @Override
    public boolean deleteMenu(Long id) {
        return removeById(id);
    }

    @Override
    public boolean updateMenu(Long id, MallMenus mallMenus) {
        mallMenus.setId(id);
        return updateById(mallMenus);
    }
}
