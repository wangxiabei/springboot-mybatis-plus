package com.spring.mapper;

import com.spring.model.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bei_ch
 * @since 2022-02-17
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}
