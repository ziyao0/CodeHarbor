package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.Instant;

/**
 * <p>
 * 应用系统
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("application")
@Entity(name = "application")
public class Application implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @TableId("app_id")
    private Long appId;

    /**
     * 应用类型 0内部系统应用 1三方平台应用 
     */
    @TableField("app_type")
    private Integer appType;

    /**
     * 客户端允许的授权类型
     */
    @TableField("authorization_grant_types")
    private String authorizationGrantTypes;

    /**
     * 授权范围
     */
    @TableField("scopes")
    private String scopes;

    /**
     * 删除状态 0正常 1失效
     */
    @TableField("state")
    private Integer state;

    /**
     * 颁发时间
     */
    @TableField("issued_at")
    private Instant issuedAt;

    /**
     * 应用秘钥
     */
    @TableField("app_secret")
    private String appSecret;

    /**
     * 应用秘钥过期时间
     */
    @TableField("app_secret_expires_at")
    private Instant appSecretExpiresAt;

    /**
     * 应用名称
     */
    @TableField("app_name")
    private String appName;

    /**
     * 系统重定向路径
     */
    @TableField("redirect_uri")
    private String redirectUri;

    @TableField("post_logout_redirect_uri")
    private String postLogoutRedirectUri;

    @TableField("token_settings")
    private String tokenSettings;

    /**
     * 系统简介
     */
    @TableField("remark")
    private String remark;


}
