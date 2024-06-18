package com.ziyao.security.oauth2.core.settings;

import org.springframework.util.Assert;

import java.io.Serial;
import java.time.Duration;
import java.util.Map;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public final class TokenSettings extends AbstractSettings {

    @Serial
    private static final long serialVersionUID = 6298615176749557599L;

    private TokenSettings(Map<String, Object> settings) {
        super(settings);
    }

    /**
     * Returns the time-to-live for an authorization code. The default is 5 minutes.
     *
     * @return the time-to-live for an authorization code
     * @since 0.4.0
     */
    public Duration getAuthorizationCodeTimeToLive() {
        return getSetting(SettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE);
    }

    /**
     * Returns the time-to-live for an access token. The default is 5 minutes.
     *
     * @return the time-to-live for an access token
     */
    public Duration getAccessTokenTimeToLive() {
        return getSetting(SettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE);
    }

    /**
     * Returns the token format for an access token. The default is
     * {@link OAuth2TokenFormat#SELF_CONTAINED}.
     *
     * @return the token format for an access token
     * @since 0.2.3
     */
    public OAuth2TokenFormat getAccessTokenFormat() {
        return getSetting(SettingNames.Token.ACCESS_TOKEN_FORMAT);
    }

    /**
     * Returns the time-to-live for a device code. The default is 5 minutes.
     *
     * @return the time-to-live for a device code
     * @since 1.1
     */
    public Duration getDeviceCodeTimeToLive() {
        return getSetting(SettingNames.Token.DEVICE_CODE_TIME_TO_LIVE);
    }

    /**
     * Returns {@code true} if refresh tokens are reused when returning the access token
     * response, or {@code false} if a new refresh token is issued. The default is
     * {@code true}.
     *
     * @return {@code true} if refresh tokens are reused when returning the access token
     * response, {@code false} otherwise
     */
    public boolean isReuseRefreshTokens() {
        return getSetting(SettingNames.Token.REUSE_REFRESH_TOKENS);
    }

    /**
     * Returns the time-to-live for a refresh token. The default is 60 minutes.
     *
     * @return the time-to-live for a refresh token
     */
    public Duration getRefreshTokenTimeToLive() {
        return getSetting(SettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE);
    }

    /**
     * Constructs a new {@link Builder} with the default settings.
     *
     * @return the {@link Builder}
     */
    public static Builder builder() {
        return new Builder().authorizationCodeTimeToLive(Duration.ofMinutes(5))
                .accessTokenTimeToLive(Duration.ofMinutes(5))
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .deviceCodeTimeToLive(Duration.ofMinutes(5))
                .reuseRefreshTokens(true)
                .refreshTokenTimeToLive(Duration.ofMinutes(60));
    }

    /**
     * Constructs a new {@link Builder} with the provided settings.
     *
     * @param settings the settings to initialize the builder
     * @return the {@link Builder}
     */
    public static Builder withSettings(Map<String, Object> settings) {
        Assert.notEmpty(settings, "settings cannot be empty");
        return new Builder().settings((s) -> s.putAll(settings));
    }

    /**
     * A builder for {@link TokenSettings}.
     */
    public static final class Builder extends AbstractBuilder<TokenSettings, Builder> {

        private Builder() {
        }

        /**
         * Set the time-to-live for an authorization code. Must be greater than
         * {@code Duration.ZERO}. A maximum authorization code lifetime of 10 minutes is
         * RECOMMENDED.
         *
         * @param authorizationCodeTimeToLive the time-to-live for an authorization code
         * @return the {@link Builder} for further configuration
         * @since 0.4.0
         */
        public Builder authorizationCodeTimeToLive(Duration authorizationCodeTimeToLive) {
            Assert.notNull(authorizationCodeTimeToLive, "authorizationCodeTimeToLive cannot be null");
            Assert.isTrue(authorizationCodeTimeToLive.getSeconds() > 0,
                    "authorizationCodeTimeToLive must be greater than Duration.ZERO");
            return setting(SettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE,
                    authorizationCodeTimeToLive);
        }

        /**
         * Set the time-to-live for an access token. Must be greater than
         * {@code Duration.ZERO}.
         *
         * @param accessTokenTimeToLive the time-to-live for an access token
         * @return the {@link Builder} for further configuration
         */
        public Builder accessTokenTimeToLive(Duration accessTokenTimeToLive) {
            Assert.notNull(accessTokenTimeToLive, "accessTokenTimeToLive cannot be null");
            Assert.isTrue(accessTokenTimeToLive.getSeconds() > 0,
                    "accessTokenTimeToLive must be greater than Duration.ZERO");
            return setting(SettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE, accessTokenTimeToLive);
        }

        /**
         * Set the token format for an access token.
         *
         * @param accessTokenFormat the token format for an access token
         * @return the {@link Builder} for further configuration
         * @since 0.2.3
         */
        public Builder accessTokenFormat(OAuth2TokenFormat accessTokenFormat) {
            Assert.notNull(accessTokenFormat, "accessTokenFormat cannot be null");
            return setting(SettingNames.Token.ACCESS_TOKEN_FORMAT, accessTokenFormat);
        }

        /**
         * Set the time-to-live for a device code. Must be greater than
         * {@code Duration.ZERO}.
         *
         * @param deviceCodeTimeToLive the time-to-live for a device code
         * @return the {@link Builder} for further configuration
         * @since 1.1
         */
        public Builder deviceCodeTimeToLive(Duration deviceCodeTimeToLive) {
            Assert.notNull(deviceCodeTimeToLive, "deviceCodeTimeToLive cannot be null");
            Assert.isTrue(deviceCodeTimeToLive.getSeconds() > 0,
                    "deviceCodeTimeToLive must be greater than Duration.ZERO");
            return setting(SettingNames.Token.DEVICE_CODE_TIME_TO_LIVE, deviceCodeTimeToLive);
        }

        /**
         * Set to {@code true} if refresh tokens are reused when returning the access
         * token response, or {@code false} if a new refresh token is issued.
         *
         * @param reuseRefreshTokens {@code true} to reuse refresh tokens, {@code false}
         *                           to issue new refresh tokens
         * @return the {@link Builder} for further configuration
         */
        public Builder reuseRefreshTokens(boolean reuseRefreshTokens) {
            return setting(SettingNames.Token.REUSE_REFRESH_TOKENS, reuseRefreshTokens);
        }

        /**
         * Set the time-to-live for a refresh token. Must be greater than
         * {@code Duration.ZERO}.
         *
         * @param refreshTokenTimeToLive the time-to-live for a refresh token
         * @return the {@link Builder} for further configuration
         */
        public Builder refreshTokenTimeToLive(Duration refreshTokenTimeToLive) {
            Assert.notNull(refreshTokenTimeToLive, "refreshTokenTimeToLive cannot be null");
            Assert.isTrue(refreshTokenTimeToLive.getSeconds() > 0,
                    "refreshTokenTimeToLive must be greater than Duration.ZERO");
            return setting(SettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE, refreshTokenTimeToLive);
        }

        /**
         * Builds the {@link TokenSettings}.
         *
         * @return the {@link TokenSettings}
         */
        @Override
        public TokenSettings build() {
            return new TokenSettings(getSettings());
        }

    }
}

