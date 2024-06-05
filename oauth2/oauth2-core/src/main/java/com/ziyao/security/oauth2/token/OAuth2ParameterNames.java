package com.ziyao.security.oauth2.token;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public abstract class OAuth2ParameterNames {
    public static final String GRANT_TYPE = "grant_type";
    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String CLIENT_ASSERTION_TYPE = "client_assertion_type";
    public static final String CLIENT_ASSERTION = "client_assertion";
    public static final String ASSERTION = "assertion";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String SCOPE = "scope";
    public static final String STATE = "state";
    public static final String CODE = "code";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String EXPIRES_IN = "expires_in";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ERROR = "error";
    public static final String ERROR_DESCRIPTION = "error_description";
    public static final String ERROR_URI = "error_uri";
    public static final String REGISTRATION_ID = "registration_id";
    public static final String TOKEN = "token";
    public static final String TOKEN_TYPE_HINT = "token_type_hint";
    public static final String DEVICE_CODE = "device_code";
    public static final String USER_CODE = "user_code";
    public static final String VERIFICATION_URI = "verification_uri";
    public static final String VERIFICATION_URI_COMPLETE = "verification_uri_complete";
    public static final String INTERVAL = "interval";
    public static final String AUDIENCE = "audience";
    public static final String RESOURCE = "resource";
    public static final String REQUESTED_TOKEN_TYPE = "requested_token_type";
    public static final String ISSUED_TOKEN_TYPE = "issued_token_type";
    public static final String SUBJECT_TOKEN = "subject_token";
    public static final String SUBJECT_TOKEN_TYPE = "subject_token_type";
    public static final String ACTOR_TOKEN = "actor_token";
    public static final String ACTOR_TOKEN_TYPE = "actor_token_type";

    private OAuth2ParameterNames() {
    }
}
