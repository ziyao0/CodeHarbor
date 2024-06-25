package com.ziyao.security.oauth2.core;

import com.ziyao.security.oauth2.core.token.OAuth2ParameterNames;

import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public record OAuth2TokenType(String value) {

    public static final OAuth2TokenType ACCESS_TOKEN = new OAuth2TokenType(OAuth2ParameterNames.ACCESS_TOKEN);

    public static final OAuth2TokenType REFRESH_TOKEN = new OAuth2TokenType(OAuth2ParameterNames.REFRESH_TOKEN);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2TokenType that = (OAuth2TokenType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
