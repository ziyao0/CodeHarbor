package com.ziyao.harbor.usercenter.authentication;

import com.ziyao.security.oauth2.core.Authentication;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public interface AuthenticationManager {

    /**
     * 认证处理 映射
     *
     * @param authentication {@link Authentication}认证核心参数
     * @return 返回认证结果
     */
    Authentication authenticate(Authentication authentication);
}
