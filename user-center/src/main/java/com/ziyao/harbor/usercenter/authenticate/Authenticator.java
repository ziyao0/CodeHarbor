package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import com.ziyao.harbor.usercenter.authenticate.query.UserQuery;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */
@FunctionalInterface
public interface Authenticator {

    /*
     * 身份验证器是否需要显式登录。
     * 如果为 false，则将用AuthenticatedUser.ANONYMOUS_USER实例化用户。
     *//*
    boolean requireAuthentication();*/

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
     * @return UserDetails
     */
    default UserDetails loadUserDetails(UserQuery query) {
        return null;
    }

    default AuthenticationType getAuthenticationType() {
        return AuthenticationType.passwd;
    }

    /**
     * 验证是否支持认证协议
     *
     * @param authenticationClass class
     * @return 返回 {@link Boolean#TRUE} 不支持
     */
    default boolean supports(Class<?> authenticationClass) {
        return Authenticator.class.isAssignableFrom(authenticationClass);
    }
}
