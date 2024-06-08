package com.ziyao.harbor.usercenter.authenticate.token.generator;

import com.alibaba.fastjson2.JSON;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.keygen.Base64StringKeyGenerator;
import com.ziyao.harbor.crypto.keygen.StringKeyGenerator;
import com.ziyao.harbor.usercenter.authenticate.token.OAuth2TokenClaimsSet;
import com.ziyao.harbor.usercenter.authenticate.token.OAuth2TokenContext;
import com.ziyao.harbor.usercenter.authenticate.token.RegisteredApp;
import com.ziyao.security.oauth2.core.OAuth2AccessToken;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public final class OAuth2AccessTokenGenerator implements OAuth2TokenGenerator<OAuth2AccessToken> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AccessTokenGenerator.class);

    private final StringKeyGenerator accessTokenGenerator = new Base64StringKeyGenerator(
            Base64.getUrlEncoder().withoutPadding(), 32);


    @Override
    public OAuth2AccessToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            return null;
        }
        RegisteredApp registeredApp = context.getRegisteredApp();

        String issuer = registeredApp.getRedirectUri();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(registeredApp.getTokenSettings().getAccessTokenTimeToLive());

        OAuth2TokenClaimsSet.Builder claimsBuilder = OAuth2TokenClaimsSet.builder();

        if (Strings.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }

        claimsBuilder
                .subject(registeredApp.getAppName())
                .audience(Collections.singletonList(String.valueOf(registeredApp.getAppid())))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .notBefore(issuedAt)
                .id(UUID.randomUUID().toString());

        if (!CollectionUtils.isEmpty(context.getAuthorizedScopes())) {
            claimsBuilder.claim(OAuth2ParameterNames.SCOPE, context.getAuthorizedScopes());
        }


        OAuth2TokenClaimsSet accessTokenClaimsSet = claimsBuilder.build();

        OAuth2AccessToken accessToken = new OAuth2AccessTokenClaims(OAuth2AccessToken.TokenType.Bearer,
                this.accessTokenGenerator.generateKey(), accessTokenClaimsSet.getIssuedAt(),
                accessTokenClaimsSet.getExpiresAt(), context.getAuthorizedScopes(), accessTokenClaimsSet.getClaims());
        if (log.isDebugEnabled()) {
            log.debug("generate access token to {}", JSON.toJSONString(accessToken));
        }
        return accessToken;
    }

    @Getter
    private static final class OAuth2AccessTokenClaims extends OAuth2AccessToken {

        private final Map<String, Object> claims;

        private OAuth2AccessTokenClaims(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt,
                                        Set<String> scopes, Map<String, Object> claims) {
            super(tokenType, tokenValue, issuedAt, expiresAt, scopes);
            this.claims = claims;
        }

    }
}
