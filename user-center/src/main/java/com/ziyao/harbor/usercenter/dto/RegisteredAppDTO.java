package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.RegisteredApp;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serial;
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
public class RegisteredAppDTO implements EntityDTO<RegisteredApp>, Serializable {

    @Serial
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
     * 系统简介
     */
    private String remark;

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<RegisteredApp> initWrapper() {

        return Wrappers.lambdaQuery(RegisteredApp.class)
                // 应用类型 0内部系统应用 1三方平台应用 
                .eq(Objects.nonNull(appType), RegisteredApp::getAppType, appType)
                // 客户端允许的授权类型
                .likeRight(Strings.hasLength(authorizationGrantTypes), RegisteredApp::getAuthorizationGrantTypes, authorizationGrantTypes)
                // 删除状态 0正常 1失效
                .eq(Objects.nonNull(state), RegisteredApp::getState, state)
                // 颁发时间
                .eq(Objects.nonNull(issuedAt), RegisteredApp::getIssuedAt, issuedAt)
                // 应用秘钥
                .likeRight(Strings.hasLength(appSecret), RegisteredApp::getAppSecret, appSecret)
                // 应用秘钥过期时间
                .eq(Objects.nonNull(appSecretExpiresAt), RegisteredApp::getAppSecretExpiresAt, appSecretExpiresAt)
                // 应用名称
                .likeRight(Strings.hasLength(appName), RegisteredApp::getAppName, appName)
                // 系统重定向路径
                .likeRight(Strings.hasLength(redirectUri), RegisteredApp::getRedirectUri, redirectUri)
                // 系统简介
                .likeRight(Strings.hasLength(remark), RegisteredApp::getRemark, remark)
                ;
    }

    @Override
    public RegisteredApp getEntity() {
        return new RegisteredApp();
    }
}
