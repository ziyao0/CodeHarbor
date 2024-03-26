package com.ziyao.oauth2.core;

import com.ziyao.harbor.core.utils.Assert;
import lombok.Getter;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
@Getter
public class OAuth2AccessToken extends AbstractOAuth2Token{

    /**
     * -- GETTER --
     *  Returns the
     * .
     *
     */
    private final TokenType tokenType;

    /**
     * -- GETTER --
     *  Returns the scope(s) associated to the token.
     *
     */
    private final Set<String> scopes;

    /**
     * Constructs an {@code OAuth2AccessToken} using the provided parameters.
     *
     * @param tokenType  the token type
     * @param tokenValue the token value
     * @param issuedAt   the time at which the token was issued
     * @param expiresAt  the expiration time on or after which the token MUST NOT be
     *                   accepted
     */
    public OAuth2AccessToken(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt) {
        this(tokenType, tokenValue, issuedAt, expiresAt, Collections.emptySet());
    }

    /**
     * Constructs an {@code OAuth2AccessToken} using the provided parameters.
     *
     * @param tokenType  the token type
     * @param tokenValue the token value
     * @param issuedAt   the time at which the token was issued
     * @param expiresAt  the expiration time on or after which the token MUST NOT be
     *                   accepted
     * @param scopes     the scope(s) associated to the token
     */
    public OAuth2AccessToken(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt,
                             Set<String> scopes) {
        super(tokenValue, issuedAt, expiresAt);
        Assert.notNull(tokenType, "tokenType cannot be null");
        this.tokenType = tokenType;
        this.scopes = Collections.unmodifiableSet((scopes != null) ? scopes : Collections.emptySet());
    }

    @Getter
    public enum TokenType {

        Bearer("Bearer"),
        ;

        private final String value;

        TokenType(String value) {
            this.value = value;
        }
    }
}
