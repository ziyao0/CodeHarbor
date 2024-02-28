package com.ziyao.harbor.oauth2.core;

import java.util.Date;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public class DefaultExpiringOAuth2RefreshToken
        extends DefaultOAuth2RefreshToken implements ExpiringOAuth2RefreshToken {
    private static final long serialVersionUID = 5792077332298877673L;
    private final Date expiration;

    /**
     * Create a new refresh token.
     *
     * @param value refresh token
     */
    public DefaultExpiringOAuth2RefreshToken(String value, Date expiration) {
        super(value);
        this.expiration = expiration;
    }


    @Override
    public Date getExpiration() {
        return expiration;
    }
}
