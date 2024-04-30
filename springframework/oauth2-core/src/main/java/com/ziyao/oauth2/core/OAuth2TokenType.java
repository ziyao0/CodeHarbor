package com.ziyao.oauth2.core;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public enum OAuth2TokenType {

    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token"),
    ;

    private final String value;

    OAuth2TokenType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
