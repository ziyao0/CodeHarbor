package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.security.oauth2.core.Authentication;

/**
 * @author ziyao zhang
 * @since 2023/9/25
 */

public interface AuthenticationProvider {

    /**
     * 身份验证
     *
     * @param authentication 需要身份验证信息
     * @return 认证后的用户信息详情
     */
    Authentication authenticate(Authentication authentication);

    /**
     * 验证是否支持认证协议
     *
     * @param authenticationClass class
     * @return 返回 {@link Boolean#TRUE} 不支持
     */
    boolean supports(Class<?> authenticationClass);
}
