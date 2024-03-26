package com.ziyao.harbor.oauth2.error;

import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public class OAuth2Error implements Serializable {


    @Serial
    private static final long serialVersionUID = 1138662310875573760L;
    private final String errorCode;

    private final String description;

    private final String uri;

    /**
     * Constructs an {@code OAuth2Error} using the provided parameters.
     *
     * @param errorCode the error code
     */
    public OAuth2Error(String errorCode) {
        this(errorCode, null, null);
    }

    /**
     * Constructs an {@code OAuth2Error} using the provided parameters.
     *
     * @param errorCode   the error code
     * @param description the error description
     * @param uri         the error uri
     */
    public OAuth2Error(String errorCode, String description, String uri) {
        Assert.hasText(errorCode, "errorCode cannot be empty");
        this.errorCode = errorCode;
        this.description = description;
        this.uri = uri;
    }

    /**
     * Returns the error code.
     *
     * @return the error code
     */
    public final String getErrorCode() {
        return this.errorCode;
    }

    /**
     * Returns the error description.
     *
     * @return the error description
     */
    public final String getDescription() {
        return this.description;
    }

    /**
     * Returns the error uri.
     *
     * @return the error uri
     */
    public final String getUri() {
        return this.uri;
    }

    @Override
    public String toString() {
        return "[" + this.getErrorCode() + "] " + ((this.getDescription() != null) ? this.getDescription() : "");
    }

}