package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.core.AuthenticatedUser;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
@FunctionalInterface
public interface Authenticator {

    /**
     * 身份验证
     *
     * @param authenticatedRequest 需要身份验证信息
     * @return 认证后的用户信息详情
     */
    AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest);

    /**
     * 获取授权后用户
     *
     * @return AuthenticatedUser
     */
    default AuthenticatedUser loadAuthenticatedUser(Long appid, String username) {
        return null;
    }

    /**
     * 验证是否支持认证协议
     *
     * @param authenticationClass class
     * @return 返回 {@link Boolean#TRUE} 不支持
     */
    default boolean supports(Class<?> authenticationClass) {
        return Authentication.class.isAssignableFrom(authenticationClass);
    }
}
