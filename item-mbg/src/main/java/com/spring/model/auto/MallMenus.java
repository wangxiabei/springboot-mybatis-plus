package com.spring.model.auto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MallMenus extends Model {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 图片
     */
    private String img;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 软删
     */
    private Date deletedAt;

    private Date createdAt;

    private Date updatedAt;

    /**
     * 1文创 2食品 3童装 4服饰
     */
    private Integer types;

    private Integer position;


}
