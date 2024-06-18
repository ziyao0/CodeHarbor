package com.ziyao.security.oauth2.core.error;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public abstract class ThrowErrors {


    public static void invalidRequestError() {
        invalidRequestError(null, null);
    }

    public static void invalidRequestError(String description) {
        invalidRequestError(description, null);
    }

    public static void invalidRequestError(String description, String uri) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST, description, uri));
    }

    public static void unauthorizedClientError(String description) {
        unauthorizedClientError(description, null);
    }

    public static void unauthorizedClientError(String description, String uri) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT, description, uri));
    }

    public static void unsupportedResponseTypeError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, description, null));
    }

    public static void invalidScopeError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE, description, null));
    }

    public static void invalidTokenError(String description, String uri) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, description, uri));
    }

    public static void serverError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, description, null));
    }

    public static void serverError(String description, String uri) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR, description, uri));
    }

    public static void invalidClientError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.INVALID_CLIENT, description, null));
    }

    public static void invalidGrantError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.INVALID_GRANT, description, null));
    }

    public static void unsupportedGrantTypeError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE, description, null));
    }

    public static void unsupportedTokenTypeError(String description) {
        throwError(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, description, "https://tools.ietf.org/html/rfc7009#section-2.2.1\""));
    }

    public static void throwError(String code, String description, String uri) {
        throwError(new OAuth2Error(code, description, uri));
    }


    public static void throwError(OAuth2Error error) {
        throw new OAuth2AuthenticationException(error);
    }


    private ThrowErrors() {
    }
}
