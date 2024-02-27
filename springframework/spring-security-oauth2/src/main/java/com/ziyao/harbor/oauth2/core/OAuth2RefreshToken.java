package com.ziyao.harbor.oauth2.core;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public interface OAuth2RefreshToken {
    /**
     * The value of the token.
     *
     * @return The value of the token.
     */
    String getValue();
}
