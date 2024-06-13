package com.ziyao.harbor.usercenter.authentication.token.oauth2.generator;

import com.ziyao.harbor.usercenter.authentication.token.oauth2.OAuth2TokenContext;
import com.ziyao.security.oauth2.core.OAuth2Token;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@FunctionalInterface
public interface OAuth2TokenGenerator<T extends OAuth2Token> {


    T generate(OAuth2TokenContext context);
}