package com.ziyao.oauth2.core;

import java.time.Instant;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class OAuth2AuthorizationCode extends AbstractOAuth2Token {

    /**
     * Constructs an {@code OAuth2AuthorizationCode} using the provided parameters.
     *
     * @param tokenValue the token value
     * @param issuedAt   the time at which the token was issued
     * @param expiresAt  the time at which the token expires
     */
    public OAuth2AuthorizationCode(String tokenValue, Instant issuedAt, Instant expiresAt) {
        super(tokenValue, issuedAt, expiresAt);
    }

}