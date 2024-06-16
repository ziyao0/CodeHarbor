package com.ziyao.security.oauth2.context;

import com.ziyao.security.oauth2.core.Authentication;

import java.io.Serializable;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public interface AuthenticationContext extends Serializable {

    /**
     * 获取当前经过身份认证的令牌
     *
     * @return 如果返回null则表示当前请求没有经过身份认证
     */
    Authentication getAuthentication();

    /**
     * 更改当前经过身份验证的主体，或删除身份验证信息。
     */
    void setAuthentication(Authentication authentication);
}
