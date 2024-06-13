package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * <p>
 * 应用系统
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Entity(name = "application")
@Data
@EqualsAndHashCode(callSuper = false)
public class Application implements Serializable {

    @Serial
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
    private Integer appType;

    /**
     * 客户端允许的授权类型
     */
    private String authorizationGrantTypes;

    /**
     * 授权范围
     */
    private String scopes;

    /**
     * 删除状态 0正常 1失效
     */
    private Integer state;

    /**
     * 颁发时间
     */
    private Instant issuedAt;

    /**
     * 应用秘钥
     */
    private String appSecret;

    /**
     * 应用秘钥过期时间
     */
    private Instant appSecretExpiresAt;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 系统重定向路径
     */
    private String redirectUri;

    private String postLogoutRedirectUri;

    private String tokenSettings;

    /**
     * 系统简介
     */
    private String remark;


}