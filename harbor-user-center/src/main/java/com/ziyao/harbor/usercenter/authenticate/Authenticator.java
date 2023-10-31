package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.security.core.AuthenticatedUser;

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
     * @param authenticateDetails 需要身份验证信息
     * @return 认证后的用户信息详情
     */
    AuthenticatedUser authenticate(AuthenticateDetails authenticateDetails);

    /**
     * 获取授权后用户
     *
     * @return AuthenticatedUser
     */
    default AuthenticatedUser loadAuthenticatedUser(Long appid, String username) {
        return null;
    }

    ;
}
