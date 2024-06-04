package com.ziyao.harbor.usercenter.authenticate.token.generate;

import com.ziyao.harbor.usercenter.authenticate.token.OAuth2TokenContext;
import com.ziyao.oauth2.core.OAuth2Token;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@FunctionalInterface
public interface OAuth2TokenGenerator<T extends OAuth2Token> {


    T generate(OAuth2TokenContext context);
}
