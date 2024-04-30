package com.ziyao.oauth2.core;

import java.time.Instant;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public class OAuth2RefreshToken extends AbstractOAuth2Token {

    /**
     * Constructs an {@code OAuth2RefreshToken} using the provided parameters.
     *
     * @param tokenValue the token value
     * @param issuedAt   the time at which the token was issued
     */
    public OAuth2RefreshToken(String tokenValue, Instant issuedAt) {
        this(tokenValue, issuedAt, null);
    }

    /**
     * Constructs an {@code OAuth2RefreshToken} using the provided parameters.
     *
     * @param tokenValue the token value
     * @param issuedAt   the time at which the token was issued
     * @param expiresAt  the time at which the token expires
     * @since 5.5
     */
    public OAuth2RefreshToken(String tokenValue, Instant issuedAt, Instant expiresAt) {
        super(tokenValue, issuedAt, expiresAt);
    }

}
