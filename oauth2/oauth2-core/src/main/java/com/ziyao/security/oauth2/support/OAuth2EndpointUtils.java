package com.ziyao.security.oauth2.support;

import com.ziyao.security.oauth2.error.OAuth2AuthenticationException;
import com.ziyao.security.oauth2.error.OAuth2Error;

/**
 * @author ziyao
 * @since 2024/06/12 10:07:50
 */
public final class OAuth2EndpointUtils {

    public static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private OAuth2EndpointUtils() {
    }

    public static void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthenticationException(error);
    }
}
