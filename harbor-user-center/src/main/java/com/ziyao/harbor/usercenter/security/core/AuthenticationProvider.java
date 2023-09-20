package com.ziyao.harbor.usercenter.security.core;

import com.ziyao.harbor.usercenter.comm.exception.AuthenticationException;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.api.ProviderName;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface AuthenticationProvider {

    /**
     * 认证处理
     *
     * @param authentication {@link Authentication}认证核心参数
     * @return 返回认证结果
     * @throws AuthenticationException 认证异常
     */
    Authentication authenticate(Authentication authentication);

    /**
     * 验证是否支持认证协议
     *
     * @param authenticationClass class
     * @return 返回 {@link Boolean#TRUE} 不支持
     */
    default boolean supports(Class<?> authenticationClass) {
        return Authentication.class.isAssignableFrom(authenticationClass);
    }

    /**
     * Provider Name
     *
     * @return {@link ProviderName}
     */
    String getProviderName();
}
