package com.spring.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bei_ch
 * @since 2022-02-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Users extends Model {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码随机数
     */
    private String salt;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 微信头像
     */
    private String wechatAvatar;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 状态 0=正常, 1=禁用
     */
    private Integer status;

    /**
     * 用户类型,0=非会员,1=会员
     */
    private Integer typee;

    /**
     * 登陆token
     */
    private String token;

    /**
     * token过期时间
     */
    private LocalDateTime tokenExpireTime;

    /**
     * 微信验证单服务区唯一码
     */
    private String openid;

    /**
     * 微信验证全服务区唯一码
     */
    private String unionid;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;



    /**
     * 用户来源: android、ios、wx_lite
     */
    private String userSource;

    /**
     * 民宿小程序openid
     */
    private String minsuWxLiteOpenid;

    /**
     * 民宿微信App对应的openid
     */
    private String minsuWxOpenid;

    /**
     * 是否房东 0不是，1是房东
     */
    private Integer isLandlord;

    /**
     * 项目注册来源 aranya_app=阿那亚app注册,aranya_minsu=阿那亚民宿注册
     */
    private String projectSource;

    /**
     * 冻结房东0正常 1冻结 2解冻
     */
    private Integer isFreeze;

    /**
     * 全域系统的主键ID
     */
    private String quanyuUserId;

    /**
     * 实名认证状态 1未认证、2已认证、3已注销
     */
    private String identityStatus;


}
