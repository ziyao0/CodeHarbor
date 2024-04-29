package com.ziyao.oauth2.error;

import lombok.Getter;
import org.springframework.util.Assert;



/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@Getter
public class OAuth2AuthenticationException extends OAuth2Exception {

    private static final long serialVersionUID = 6276324810720064719L;

    /**
     * -- GETTER --
     * Returns the
     * .
     */
    private final OAuth2Error error;

    /**
     * Constructs an {@code OAuth2AuthenticationException} using the provided parameters.
     *
     * @param errorCode the {@link OAuth2ErrorCodes OAuth 2.0 Error Code}
     * @since 5.5
     */
    public OAuth2AuthenticationException(String errorCode) {
        this(new OAuth2Error(errorCode));
    }

    /**
     * Constructs an {@code OAuth2AuthenticationException} using the provided parameters.
     *
     * @param error the {@link OAuth2Error OAuth 2.0 Error}
     */
    public OAuth2AuthenticationException(OAuth2Error error) {
        this(error, error.getDescription());
    }

    /**
     * Constructs an {@code OAuth2AuthenticationException} using the provided parameters.
     *
     * @param error the {@link OAuth2Error OAuth 2.0 Error}
     * @param cause the root cause
     */
    public OAuth2AuthenticationException(OAuth2Error error, Throwable cause) {
        this(error, cause.getMessage(), cause);
    }

    /**
     * Constructs an {@code OAuth2AuthenticationException} using the provided parameters.
     *
     * @param error   the {@link OAuth2Error OAuth 2.0 Error}
     * @param message the detail message
     */
    public OAuth2AuthenticationException(OAuth2Error error, String message) {
        this(error, message, null);
    }

    /**
     * Constructs an {@code OAuth2AuthenticationException} using the provided parameters.
     *
     * @param error   the {@link OAuth2Error OAuth 2.0 Error}
     * @param message the detail message
     * @param cause   the root cause
     */
    public OAuth2AuthenticationException(OAuth2Error error, String message, Throwable cause) {
        super(message, cause);
        Assert.notNull(error, "error cannot be null");
        this.error = error;
    }

}
