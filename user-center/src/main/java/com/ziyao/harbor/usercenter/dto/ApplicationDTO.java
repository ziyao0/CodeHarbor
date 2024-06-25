package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.Application;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 应用系统
 * </p>
 *
 * @author zhangziyao
 * @since 2024-06-08
 */
@Data
public class ApplicationDTO implements EntityDTO<Application>, Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
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
    private LocalDateTime issuedAt;
    /**
     * 应用秘钥
     */
    private String appSecret;
    /**
     * 应用秘钥过期时间
     */
    private LocalDateTime appSecretExpiresAt;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 系统重定向路径
     */
    private String redirectUri;
    /**
     *
     */
    private String postLogoutRedirectUri;
    /**
     *
     */
    private String tokenSettings;
    /**
     * 系统简介
     */
    private String remark;

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<Application> initWrapper() {

        return Wrappers.lambdaQuery(Application.class)
                // 应用类型 0内部系统应用 1三方平台应用 
                .eq(Objects.nonNull(appType), Application::getAppType, appType)
                // 客户端允许的授权类型
                .likeRight(Strings.hasLength(authorizationGrantTypes), Application::getAuthorizationGrantTypes, authorizationGrantTypes)
                // 授权范围
                .likeRight(Strings.hasLength(scopes), Application::getScopes, scopes)
                // 删除状态 0正常 1失效
                .eq(Objects.nonNull(state), Application::getState, state)
                // 颁发时间
                .eq(Objects.nonNull(issuedAt), Application::getIssuedAt, issuedAt)
                // 应用秘钥
                .likeRight(Strings.hasLength(appSecret), Application::getAppSecret, appSecret)
                // 应用秘钥过期时间
                .eq(Objects.nonNull(appSecretExpiresAt), Application::getAppSecretExpiresAt, appSecretExpiresAt)
                // 应用名称
                .likeRight(Strings.hasLength(appName), Application::getAppName, appName)
                // 系统重定向路径
                .likeRight(Strings.hasLength(redirectUri), Application::getRedirectUri, redirectUri)
                // 
                .likeRight(Strings.hasLength(postLogoutRedirectUri), Application::getPostLogoutRedirectUri, postLogoutRedirectUri)
                // 
                .likeRight(Strings.hasLength(tokenSettings), Application::getTokenSettings, tokenSettings)
                // 系统简介
                .likeRight(Strings.hasLength(remark), Application::getRemark, remark)
                ;
    }

    @Override
    public Application getEntity() {
        return new Application();
    }
}
