package com.ziyao.harbor.usercenter.authenticate.token.generate;

import com.ziyao.harbor.crypto.keygen.Base64StringKeyGenerator;
import com.ziyao.harbor.crypto.keygen.StringKeyGenerator;
import com.ziyao.harbor.usercenter.authenticate.token.OAuth2TokenContext;
import com.ziyao.security.oauth2.core.OAuth2RefreshToken;
import com.ziyao.security.oauth2.core.OAuth2TokenType;

import java.time.Instant;
import java.util.Base64;

/**
 * @author ziyao zhang
 * @since 2024/3/27
 */
public class OAuth2RefreshTokenGenerator implements OAuth2TokenGenerator<OAuth2RefreshToken> {


    private final StringKeyGenerator refreshTokenGenerator = new Base64StringKeyGenerator(
            Base64.getUrlEncoder().withoutPadding(), 32);


    @Override
    public OAuth2RefreshToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.REFRESH_TOKEN.equals(context.getTokenType())) {
            return null;
        }

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(context.getRegisteredApp().getTokenSettings().getRefreshTokenTimeToLive());
        return new OAuth2RefreshToken(this.refreshTokenGenerator.generateKey(), issuedAt, expiresAt);
    }
}
