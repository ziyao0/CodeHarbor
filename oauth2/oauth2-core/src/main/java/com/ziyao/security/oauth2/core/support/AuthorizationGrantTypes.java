package com.ziyao.security.oauth2.core.support;

import com.ziyao.security.oauth2.core.AuthorizationGrantType;

/**
 * @author ziyao
 * @since 2024/06/09 10:35:04
 */
public abstract class AuthorizationGrantTypes {

    private AuthorizationGrantTypes() {
    }

    public static AuthorizationGrantType resolve(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.value().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.value().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.value().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        } else if (AuthorizationGrantType.DEVICE_CODE.value().equals(authorizationGrantType)) {
            return AuthorizationGrantType.DEVICE_CODE;
        }
        return new AuthorizationGrantType(authorizationGrantType);
    }
}
