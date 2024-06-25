package com.ziyao.security.oauth2.core.token.validators;

import com.ziyao.security.oauth2.core.OAuth2Token;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public interface OAuth2TokenValidator<T extends OAuth2Token> {

    void validate(T token);
}
