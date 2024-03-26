package com.ziyao.oauth2.error;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public class InvalidBearerTokenException extends OAuth2AuthenticationException {

    @Serial
    private static final long serialVersionUID = 4421091380179524396L;

    /**
     * Construct an instance of {@link InvalidBearerTokenException} given the provided
     * description.
     * <p>
     * The description will be wrapped into an
     * {@link OAuth2Error} instance as the
     * {@code error_description}.
     *
     * @param description the description
     */
    public InvalidBearerTokenException(String description) {
        super(BearerTokenErrors.invalidToken(description));
    }

    /**
     * Construct an instance of {@link InvalidBearerTokenException} given the provided
     * description and cause
     * <p>
     * The description will be wrapped into an
     * {@link OAuth2Error} instance as the
     * {@code error_description}.
     *
     * @param description the description
     * @param cause       the causing exception
     */
    public InvalidBearerTokenException(String description, Throwable cause) {
        super(BearerTokenErrors.invalidToken(description), cause);
    }

}
