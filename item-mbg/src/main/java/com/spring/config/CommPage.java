package com.spring.config;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页数据封装类
 * @param <T>
 */
@Data
public class CommPage<T> {

    private Integer pageSize;
    private Integer pageNum;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    /**
     * 将MyBatis Plus 分页结果转化为通用结果
     * @param pageResult
     * @param <T>
     * @return
     */
    public static <T> CommPage<T> restPage(Page<T> pageResult){
        CommPage<T> result = new CommPage<>();
        result.setPageNum(Convert.toInt(pageResult.getCurrent()));
        result.setPageSize(Convert.toInt(pageResult.getSize()));
        result.setTotal(pageResult.getTotal());
        result.setTotalPage(Convert.toInt(pageResult.getTotal()/pageResult.getSize() + 1));
        result.setList(pageResult.getRecords());
        return result;
    }



}
