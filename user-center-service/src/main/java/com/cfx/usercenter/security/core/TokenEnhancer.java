package com.cfx.usercenter.security.core;

import com.cfx.usercenter.security.api.AccessToken;

/**
 * @author ziyao zhang
 * @since 2023/5/9
 */
public interface TokenEnhancer {


    /**
     * 在创建供客户端使用的新令牌的过程中，提供自定义访问令牌的机会
     * <p>
     * （例如，通过其附加信息映射）。
     *
     * @param accessToken 当前访问令牌
     * @return 返回增强后的token令牌
     */
    default AccessToken enhance(AccessToken accessToken) {
        return accessToken;
    }
}
