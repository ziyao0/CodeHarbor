package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.Authorization;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-13
 */
@Data
public class AuthorizationDTO implements EntityDTO<Authorization>, Serializable {

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
     * 
     */
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
    private LocalDateTime authorizationCodeIssuedAt;
    /**
     * 授权码失效时间
     */
    private LocalDateTime authorizationCodeExpiresAt;
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
    private LocalDateTime accessTokenIssuedAt;
    /**
     * 认证临牌失效时间
     */
    private LocalDateTime accessTokenExpiresAt;
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
    private LocalDateTime refreshTokenIssuedAt;
    /**
     * 刷新令牌过期时间
     */
    private LocalDateTime refreshTokenExpiresAt;
    /**
     * 元数据信息
     */
    private String refreshTokenMetadata;

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<Authorization> initWrapper() {

        return Wrappers.lambdaQuery(Authorization.class)
                // 应用系统id
                .eq(Objects.nonNull(appid), Authorization::getAppid, appid)
                // 
                .eq(Objects.nonNull(userId), Authorization::getUserId, userId)
                // 授权类型
                .likeRight(Strings.hasLength(authorizationGrantType), Authorization::getAuthorizationGrantType, authorizationGrantType)
                // 授权范围
                .likeRight(Strings.hasLength(authorizedScopes), Authorization::getAuthorizedScopes, authorizedScopes)
                // 授权附加属性
                .likeRight(Strings.hasLength(attributes), Authorization::getAttributes, attributes)
                // 状态 1正常 2失效
                .eq(Objects.nonNull(state), Authorization::getState, state)
                // 授权码值
                .likeRight(Strings.hasLength(authorizationCodeValue), Authorization::getAuthorizationCodeValue, authorizationCodeValue)
                // 授权码颁发时间
                .eq(Objects.nonNull(authorizationCodeIssuedAt), Authorization::getAuthorizationCodeIssuedAt, authorizationCodeIssuedAt)
                // 授权码失效时间
                .eq(Objects.nonNull(authorizationCodeExpiresAt), Authorization::getAuthorizationCodeExpiresAt, authorizationCodeExpiresAt)
                // 授权码元数据信息
                .likeRight(Strings.hasLength(authorizationCodeMetadata), Authorization::getAuthorizationCodeMetadata, authorizationCodeMetadata)
                // 认证令牌值
                .likeRight(Strings.hasLength(accessTokenValue), Authorization::getAccessTokenValue, accessTokenValue)
                // 认证令牌颁发时间
                .eq(Objects.nonNull(accessTokenIssuedAt), Authorization::getAccessTokenIssuedAt, accessTokenIssuedAt)
                // 认证临牌失效时间
                .eq(Objects.nonNull(accessTokenExpiresAt), Authorization::getAccessTokenExpiresAt, accessTokenExpiresAt)
                // 认证令牌元数据信息
                .likeRight(Strings.hasLength(accessTokenMetadata), Authorization::getAccessTokenMetadata, accessTokenMetadata)
                // 认证令牌类型
                .likeRight(Strings.hasLength(accessTokenType), Authorization::getAccessTokenType, accessTokenType)
                // 认证令牌范围
                .likeRight(Strings.hasLength(accessTokenScopes), Authorization::getAccessTokenScopes, accessTokenScopes)
                // 刷新令牌值
                .likeRight(Strings.hasLength(refreshTokenValue), Authorization::getRefreshTokenValue, refreshTokenValue)
                // 刷新令牌颁发时间
                .eq(Objects.nonNull(refreshTokenIssuedAt), Authorization::getRefreshTokenIssuedAt, refreshTokenIssuedAt)
                // 刷新令牌过期时间
                .eq(Objects.nonNull(refreshTokenExpiresAt), Authorization::getRefreshTokenExpiresAt, refreshTokenExpiresAt)
                // 元数据信息
                .likeRight(Strings.hasLength(refreshTokenMetadata), Authorization::getRefreshTokenMetadata, refreshTokenMetadata)
                ;
    }

    @Override
    public Authorization getEntity() {
        return new Authorization();
    }
}
