package com.ziyao.oauth2.token;

import com.ziyao.oauth2.core.OAuth2RefreshToken;
import com.ziyao.oauth2.core.OAuth2TokenType;

/**
 * @author ziyao zhang
 * @since 2024/3/27
 */
public class OAuth2RefreshTokenGenerator implements OAuth2TokenGenerator<OAuth2RefreshToken> {

    @Override
    public OAuth2RefreshToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.REFRESH_TOKEN.equals(context.getTokenType())) {
            return null;
        }
        return null;
    }
}
