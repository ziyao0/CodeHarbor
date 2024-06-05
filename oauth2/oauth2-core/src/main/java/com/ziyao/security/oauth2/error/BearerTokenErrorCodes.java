package com.ziyao.security.oauth2.error;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public interface BearerTokenErrorCodes {


    /**
     * {@code invalid_request} - The request is missing a required parameter, includes an
     * unsupported parameter or parameter value, repeats the same parameter, uses more
     * than one method for including an access token, or is otherwise malformed.
     */
    public static final String INVALID_REQUEST = "invalid_request";

    /**
     * {@code invalid_token} - The access token provided is expired, revoked, malformed,
     * or invalid for other reasons.
     */
    public static final String INVALID_TOKEN = "invalid_token";

    /**
     * {@code insufficient_scope} - The request requires higher privileges than provided
     * by the access token.
     */
    public static final String INSUFFICIENT_SCOPE = "insufficient_scope";

}
