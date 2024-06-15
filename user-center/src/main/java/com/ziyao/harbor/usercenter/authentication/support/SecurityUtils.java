package com.ziyao.harbor.usercenter.authentication.support;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public abstract class SecurityUtils {


    /**
     * 判断是否为没有经过身份验证
     *
     * @param authentication 待验证对象
     * @return {@code true} 验证失败的认证对象
     */
    public static boolean isUnauthorized(Authentication authentication) {
        return !isAuthorized(authentication);
    }

    /**
     * 判断是否为经过身份验证
     *
     * @param authentication 待验证对象
     * @return {@code true} 已经验证通过的
     */
    public static boolean isAuthorized(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();

    }
}
