package com.ziyao.security.oauth2.core;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public enum AuthorizationGrantType {
    AUTHORIZATION_CODE("authorization_code"),
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password"),
    JWT_BEARER("urn:ietf:params:oauth:grant-type:jwt-bearer"),
    DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code"),
    ;


    private final String value;

    AuthorizationGrantType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
