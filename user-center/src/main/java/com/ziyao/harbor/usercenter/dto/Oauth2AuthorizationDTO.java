package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.Oauth2Authorization;
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
 * @since 2024-06-08
 */
@Data
public class Oauth2AuthorizationDTO implements EntityDTO<Oauth2Authorization>, Serializable {

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
    public LambdaQueryWrapper<Oauth2Authorization> initWrapper() {

        return Wrappers.lambdaQuery(Oauth2Authorization.class)
                // 应用系统id
                .eq(Objects.nonNull(appid), Oauth2Authorization::getAppid, appid)
                // 应用名称
                .likeRight(Strings.hasLength(appName), Oauth2Authorization::getAppName, appName)
                // 
                .eq(Objects.nonNull(userId), Oauth2Authorization::getUserId, userId)
                // 授权类型
                .likeRight(Strings.hasLength(authorizationGrantType), Oauth2Authorization::getAuthorizationGrantType, authorizationGrantType)
                // 授权范围
                .likeRight(Strings.hasLength(authorizedScopes), Oauth2Authorization::getAuthorizedScopes, authorizedScopes)
                // 授权附加属性
                .likeRight(Strings.hasLength(attributes), Oauth2Authorization::getAttributes, attributes)
                // 状态 1正常 2失效
                .eq(Objects.nonNull(state), Oauth2Authorization::getState, state)
                // 授权码值
                .likeRight(Strings.hasLength(authorizationCodeValue), Oauth2Authorization::getAuthorizationCodeValue, authorizationCodeValue)
                // 授权码颁发时间
                .eq(Objects.nonNull(authorizationCodeIssuedAt), Oauth2Authorization::getAuthorizationCodeIssuedAt, authorizationCodeIssuedAt)
                // 授权码失效时间
                .eq(Objects.nonNull(authorizationCodeExpiresAt), Oauth2Authorization::getAuthorizationCodeExpiresAt, authorizationCodeExpiresAt)
                // 授权码元数据信息
                .likeRight(Strings.hasLength(authorizationCodeMetadata), Oauth2Authorization::getAuthorizationCodeMetadata, authorizationCodeMetadata)
                // 认证令牌值
                .likeRight(Strings.hasLength(accessTokenValue), Oauth2Authorization::getAccessTokenValue, accessTokenValue)
                // 认证令牌颁发时间
                .eq(Objects.nonNull(accessTokenIssuedAt), Oauth2Authorization::getAccessTokenIssuedAt, accessTokenIssuedAt)
                // 认证临牌失效时间
                .eq(Objects.nonNull(accessTokenExpiresAt), Oauth2Authorization::getAccessTokenExpiresAt, accessTokenExpiresAt)
                // 认证令牌元数据信息
                .likeRight(Strings.hasLength(accessTokenMetadata), Oauth2Authorization::getAccessTokenMetadata, accessTokenMetadata)
                // 认证令牌类型
                .likeRight(Strings.hasLength(accessTokenType), Oauth2Authorization::getAccessTokenType, accessTokenType)
                // 认证令牌范围
                .likeRight(Strings.hasLength(accessTokenScopes), Oauth2Authorization::getAccessTokenScopes, accessTokenScopes)
                // 刷新令牌值
                .likeRight(Strings.hasLength(refreshTokenValue), Oauth2Authorization::getRefreshTokenValue, refreshTokenValue)
                // 刷新令牌颁发时间
                .eq(Objects.nonNull(refreshTokenIssuedAt), Oauth2Authorization::getRefreshTokenIssuedAt, refreshTokenIssuedAt)
                // 刷新令牌过期时间
                .eq(Objects.nonNull(refreshTokenExpiresAt), Oauth2Authorization::getRefreshTokenExpiresAt, refreshTokenExpiresAt)
                // 元数据信息
                .likeRight(Strings.hasLength(refreshTokenMetadata), Oauth2Authorization::getRefreshTokenMetadata, refreshTokenMetadata)
                ;
    }

    @Override
    public Oauth2Authorization getEntity() {
        return new Oauth2Authorization();
    }
}
