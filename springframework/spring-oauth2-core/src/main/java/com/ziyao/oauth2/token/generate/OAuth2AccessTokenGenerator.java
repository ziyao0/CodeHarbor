package com.ziyao.oauth2.token.generate;

import com.ziyao.oauth2.core.OAuth2AccessToken;
import com.ziyao.oauth2.core.OAuth2TokenType;
import com.ziyao.oauth2.token.OAuth2TokenContext;

import java.time.Instant;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public class OAuth2AccessTokenGenerator implements OAuth2TokenGenerator<OAuth2AccessToken> {
    @Override
    public OAuth2AccessToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            return null;
        }

        Instant issuedAt = Instant.now();

        return null;
    }
}
