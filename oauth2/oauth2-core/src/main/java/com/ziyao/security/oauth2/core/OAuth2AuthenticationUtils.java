package com.ziyao.security.oauth2.core;

import com.ziyao.security.oauth2.core.settings.OAuth2TokenFormat;
import com.ziyao.security.oauth2.core.token.OAuth2TokenContext;

/**
 * @author ziyao
 * @since 2024/06/18 16:09:24
 */
public abstract class OAuth2AuthenticationUtils {


    public static <T extends OAuth2Token> OAuth2AccessToken accessToken(
            OAuth2Authorization.Builder builder, T token, OAuth2TokenContext tokenContext) {

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.Bearer, token.getTokenValue(),
                token.getIssuedAt(), token.getExpiresAt(), tokenContext.getAuthorizedScopes());

        OAuth2TokenFormat accessTokenFormat = tokenContext.getRegisteredApp()
                .getTokenSettings()
                .getAccessTokenFormat();
        builder.token(accessToken, metadata -> {

            // TODO 如何设置额外数据
            if (token instanceof ClaimAccessor claimAccessor) {
                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, claimAccessor.getClaims());
            }
            metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, false);
//            metadata.put(OAuth2TokenFormat.class.getName(), accessTokenFormat.value());
        });
        return accessToken;
    }


    public static <T extends OAuth2Token> OAuth2Authorization invalidate(OAuth2Authorization authentication, T token) {


        OAuth2Authorization.Builder builder = OAuth2Authorization.from(authentication)
                .token(token, metadata -> metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true));

        if (OAuth2RefreshToken.class.isAssignableFrom(token.getClass())) {

            builder.token(
                    authentication.getAccessToken().getToken(),
                    metadata -> metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true));

            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authentication.getToken(OAuth2AuthorizationCode.class);
            if (authorizationCodeToken != null && !authorizationCodeToken.isInvalidated()) {

                builder.token(
                        authorizationCodeToken.getToken(),
                        metadata -> metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, true));
            }
        }
        return builder.build();
    }

    private OAuth2AuthenticationUtils() {
    }
}
