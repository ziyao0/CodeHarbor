package com.ziyao.harbor.oauth2.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public class DefaultOAuth2RefreshToken implements Serializable, OAuth2RefreshToken {
    private static final long serialVersionUID = -2898234098159868942L;
    private final String value;

    /**
     * Create a new refresh token.
     */
    public DefaultOAuth2RefreshToken(String value) {
        this.value = value;
    }

    /**
     * Default constructor for JPA and other serialization tools.
     */
    @SuppressWarnings("unused")
    private DefaultOAuth2RefreshToken() {
        this(null);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultOAuth2RefreshToken that = (DefaultOAuth2RefreshToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

