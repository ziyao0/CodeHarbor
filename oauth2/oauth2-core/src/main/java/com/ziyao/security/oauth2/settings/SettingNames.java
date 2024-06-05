package com.ziyao.security.oauth2.settings;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public abstract class SettingNames {

    private static final String SETTINGS_NAMESPACE = "settings.";

    private SettingNames() {
    }


    /**
     * The names for token configuration settings.
     */
    public static final class Token {

        private static final String TOKEN_SETTINGS_NAMESPACE = SETTINGS_NAMESPACE.concat("token.");

        /**
         * Set the time-to-live for an authorization code.
         *
         * @since 0.4.0
         */
        public static final String AUTHORIZATION_CODE_TIME_TO_LIVE = TOKEN_SETTINGS_NAMESPACE
                .concat("authorization-code-time-to-live");

        /**
         * Set the time-to-live for an access token.
         */
        public static final String ACCESS_TOKEN_TIME_TO_LIVE = TOKEN_SETTINGS_NAMESPACE
                .concat("access-token-time-to-live");

        /**
         * Set the {@link OAuth2TokenFormat token format} for an access token.
         *
         * @since 0.2.3
         */
        public static final String ACCESS_TOKEN_FORMAT = TOKEN_SETTINGS_NAMESPACE.concat("access-token-format");

        /**
         * Set the time-to-live for a device code.
         *
         * @since 1.1
         */
        public static final String DEVICE_CODE_TIME_TO_LIVE = TOKEN_SETTINGS_NAMESPACE
                .concat("device-code-time-to-live");

        /**
         * Set to {@code true} if refresh tokens are reused when returning the access
         * token response, or {@code false} if a new refresh token is issued.
         */
        public static final String REUSE_REFRESH_TOKENS = TOKEN_SETTINGS_NAMESPACE.concat("reuse-refresh-tokens");

        /**
         * Set the time-to-live for a refresh token.
         */
        public static final String REFRESH_TOKEN_TIME_TO_LIVE = TOKEN_SETTINGS_NAMESPACE
                .concat("refresh-token-time-to-live");


        /**
         * Set to {@code true} if access tokens must be bound to the client
         * {@code X509Certificate} received during client authentication when using the
         * {@code tls_client_auth} or {@code self_signed_tls_client_auth} method.
         *
         * @since 1.3
         */
        public static final String X509_CERTIFICATE_BOUND_ACCESS_TOKENS = TOKEN_SETTINGS_NAMESPACE
                .concat("x509-certificate-bound-access-tokens");

        private Token() {
        }

    }
}
