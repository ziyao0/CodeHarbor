package com.ziyao.oauth2.core;

import com.ziyao.harbor.core.lang.Nullable;
import com.ziyao.harbor.core.utils.Assert;
import lombok.Getter;

import java.time.Instant;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public abstract class AbstractOAuth2Token implements OAuth2Token {

    /**
     * -- GETTER --
     * Returns the token value.
     */
    @Getter
    private final String tokenValue;

    private final Instant issuedAt;

    private final Instant expiresAt;

    /**
     * Sub-class constructor.
     *
     * @param tokenValue the token value
     */
    protected AbstractOAuth2Token(String tokenValue) {
        this(tokenValue, null, null);
    }

    /**
     * Sub-class constructor.
     *
     * @param tokenValue the token value
     * @param issuedAt   the time at which the token was issued, may be {@code null}
     * @param expiresAt  the expiration time on or after which the token MUST NOT be
     *                   accepted, may be {@code null}
     */
    protected AbstractOAuth2Token(String tokenValue, @Nullable Instant issuedAt, @Nullable Instant expiresAt) {
        Assert.hasText(tokenValue, "tokenValue cannot be empty");
        if (issuedAt != null && expiresAt != null) {
            Assert.isTrue(expiresAt.isAfter(issuedAt), "expiresAt must be after issuedAt");
        }
        this.tokenValue = tokenValue;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    /**
     * Returns the time at which the token was issued.
     *
     * @return the time the token was issued or {@code null}
     */
    @Nullable
    public Instant getIssuedAt() {
        return this.issuedAt;
    }

    /**
     * Returns the expiration time on or after which the token MUST NOT be accepted.
     *
     * @return the token expiration time or {@code null}
     */
    @Nullable
    public Instant getExpiresAt() {
        return this.expiresAt;
    }
}
