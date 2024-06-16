package com.ziyao.harbor.usercenter.authentication.token;

import com.ziyao.security.oauth2.core.OAuth2Token;
import com.ziyao.security.oauth2.token.OAuth2TokenContext;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@FunctionalInterface
public interface OAuth2TokenGenerator<T extends OAuth2Token> {


    T generate(OAuth2TokenContext context);
}
