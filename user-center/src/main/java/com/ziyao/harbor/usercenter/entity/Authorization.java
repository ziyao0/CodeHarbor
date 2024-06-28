package com.ziyao.harbor.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@Data
@RedisHash
@EqualsAndHashCode(callSuper = false)
@TableName("authorization")
@Entity(name = "authorization")
public class Authorization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @TableId("id")
    private Long id;

    /**
     * 应用系统id
     */
    @TableField("appid")
    private Long appid;

    @TableField("user_id")
    private Long userId;

    /**
     * 授权类型
     */
    @TableField("authorization_grant_type")
    private String authorizationGrantType;

    /**
     * 授权范围
     */
    @TableField("authorized_scopes")
    private String authorizedScopes;

    /**
     * 授权附加属性
     */
    @TableField("attributes")
    private String attributes;

    /**
     * 状态 1正常 2失效
     */
    @TableField("state")
    private Integer state;

    /**
     * 授权码值
     */
    @TableField("authorization_code_value")
    private String authorizationCodeValue;

    /**
     * 授权码颁发时间
     */
    @TableField("authorization_code_issued_at")
    private LocalDateTime authorizationCodeIssuedAt;

    /**
     * 授权码失效时间
     */
    @TableField("authorization_code_expires_at")
    private LocalDateTime authorizationCodeExpiresAt;

    /**
     * 授权码元数据信息
     */
    @TableField("authorization_code_metadata")
    private String authorizationCodeMetadata;

    /**
     * 认证令牌值
     */
    @TableField("access_token_value")
    private String accessTokenValue;

    /**
     * 认证令牌颁发时间
     */
    @TableField("access_token_issued_at")
    private LocalDateTime accessTokenIssuedAt;

    /**
     * 认证临牌失效时间
     */
    @TableField("access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    /**
     * 认证令牌元数据信息
     */
    @TableField("access_token_metadata")
    private String accessTokenMetadata;

    /**
     * 认证令牌类型
     */
    @TableField("access_token_type")
    private String accessTokenType;

    /**
     * 认证令牌范围
     */
    @TableField("access_token_scopes")
    private String accessTokenScopes;

    /**
     * 刷新令牌值
     */
    @TableField("refresh_token_value")
    private String refreshTokenValue;

    /**
     * 刷新令牌颁发时间
     */
    @TableField("refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;

    /**
     * 刷新令牌过期时间
     */
    @TableField("refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;

    /**
     * 元数据信息
     */
    @TableField("refresh_token_metadata")
    private String refreshTokenMetadata;
    /**
     * id令牌
     */
    @TableField("oidc_id_token_value")
    private String oidcIdTokenValue;

    /**
     * id令牌颁发时间
     */
    @TableField("oidc_id_token_issued_at")
    private LocalDateTime oidcIdTokenIssuedAT;

    /**
     * id令牌过期时间
     */
    @TableField("oidc_id_token_expires_at")
    private LocalDateTime oidcIdTokenExpiresAt;
    /**
     * id令牌声明
     */
    @TableField("oidc_id_token_claims")
    private String oidcIdTokenClaims;

    /**
     * id令牌元数据信息
     */
    @TableField("oidc_id_token_metadata")
    private String oidcIdTokenMetadata;

}
