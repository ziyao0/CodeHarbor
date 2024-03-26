package com.ziyao.oauth2.error;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public abstract class ThrowErrors {


    private static void throwError(OAuth2Error error) {
        throw new OAuth2AuthenticationException(error);
    }

    private ThrowErrors() {
    }
}
