package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("authorization")
public class Authorization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 应用系统id
     */
    private Long appid;

    /**
     * 应用名称
     */
    private String appName;

    private Long userId;

    /**
     * 授权类型
     */
    private String authorizationGrantType;

    /**
     * 授权范围
     */
    private String authorizedScopes;

    /**
     * 授权附加属性
     */
    private String attributes;

    /**
     * 状态 1正常 2失效
     */
    private Integer state;

    /**
     * 授权码值
     */
    private String authorizationCodeValue;

    /**
     * 授权码颁发时间
     */
    private Instant authorizationCodeIssuedAt;

    /**
     * 授权码失效时间
     */
    private Instant authorizationCodeExpiresAt;

    /**
     * 授权码元数据信息
     */
    private String authorizationCodeMetadata;

    /**
     * 认证令牌值
     */
    private String accessTokenValue;

    /**
     * 认证令牌颁发时间
     */
    private Instant accessTokenIssuedAt;

    /**
     * 认证临牌失效时间
     */
    private Instant accessTokenExpiresAt;

    /**
     * 认证令牌元数据信息
     */
    private String accessTokenMetadata;

    /**
     * 认证令牌类型
     */
    private String accessTokenType;

    /**
     * 认证令牌范围
     */
    private String accessTokenScopes;

    /**
     * 刷新令牌值
     */
    private String refreshTokenValue;

    /**
     * 刷新令牌颁发时间
     */
    private Instant refreshTokenIssuedAt;

    /**
     * 刷新令牌过期时间
     */
    private Instant refreshTokenExpiresAt;

    /**
     * 元数据信息
     */
    private String refreshTokenMetadata;


}
