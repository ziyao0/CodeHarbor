package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AccessTokenAuthenticationToken;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2RefreshTokenAuthenticationToken;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2TokenGenerator;
import com.ziyao.harbor.usercenter.service.app.RegisteredAppService;
import com.ziyao.harbor.usercenter.service.oauth2.OAuth2AuthorizationService;
import com.ziyao.security.oauth2.core.*;
import com.ziyao.security.oauth2.core.error.ThrowErrors;
import com.ziyao.security.oauth2.core.token.DefaultOAuth2TokenContext;
import com.ziyao.security.oauth2.core.token.OAuth2TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Slf4j
@Component
public class OAuth2RefreshTokenAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private final OAuth2AuthorizationService authorizationService;
    private final RegisteredAppService registeredAppService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public OAuth2RefreshTokenAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                    RegisteredAppService registeredAppService,
                                                    OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.authorizationService = authorizationService;
        this.registeredAppService = registeredAppService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        OAuth2RefreshTokenAuthenticationToken authenticationToken = (OAuth2RefreshTokenAuthenticationToken) authentication;

        Authentication principal = (Authentication) authenticationToken.getPrincipal();
        // 请求刷新令牌
        String refreshTokenValue = authenticationToken.getRefreshToken();
        // 获取刷新令牌
        OAuth2Authorization authorization = authorizationService.findByToken(refreshTokenValue, OAuth2TokenType.REFRESH_TOKEN);
        if (authorization == null) {
            if (log.isDebugEnabled()) {
                log.debug("无效请求：刷新令牌无效");
            }
            ThrowErrors.invalidGrantError(refreshTokenValue);
        }
        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = authorization.getRefreshToken();

        if (refreshToken == null || !refreshToken.isActive()) {
            if (log.isDebugEnabled()) {
                log.debug("无效请求：refreshToken超出活跃范围，注册应用：{}", "appid");
            }
            ThrowErrors.invalidGrantError(refreshTokenValue);
        }

        Set<String> scopes = authenticationToken.getScopes();
        Set<String> authorizedScopes = authorization.getAuthorizedScopes();

        if (!authorizedScopes.containsAll(scopes)) {
            ThrowErrors.invalidScopeError(Strings.collectionToCommaDelimitedString(scopes));
        }
        if (log.isDebugEnabled()) {
            log.trace("Validated token request parameters");
        }
        if (scopes.isEmpty()) {
            scopes = authorizedScopes;
        }

        RegisteredApp registeredApp = registeredAppService.findById(authorization.getAppId());
        if (registeredApp == null) {
            ThrowErrors.invalidClientError(authorization.getAppId().toString());
        }
        // @formatter:off
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredApp(registeredApp)
                .principal(principal)
                .authorization(authorization)
                .authorizedScopes(scopes)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
        // @formatter:on

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.from(authorization);

        // 生成accessToken
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        OAuth2AccessToken accessToken = OAuth2AuthenticationUtils.accessToken(authorizationBuilder, generatedAccessToken, tokenContext);

        // ----- Refresh token -----
        OAuth2RefreshToken currentRefToken = refreshToken.getToken();
        if (!registeredApp.getTokenSettings().isReuseRefreshTokens()) {
            OAuth2TokenContext context = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(context);
            if (generatedRefreshToken != null) {
                if (generatedRefreshToken instanceof OAuth2RefreshToken) {

                    if (log.isDebugEnabled()) {
                        log.debug("Generated Refresh token: {}", generatedRefreshToken);
                    }
                    currentRefToken = (OAuth2RefreshToken) generatedRefreshToken;
                    authorizationBuilder.refreshToken(currentRefToken);
                } else {
                    ThrowErrors.serverError("The token generator failed to generate a valid refresh token.", ERROR_URI);
                }
            }
        }
        // 保存令牌
        authorization = authorizationBuilder.build();

        this.authorizationService.save(authorization);
        if (log.isDebugEnabled()) {
            log.debug("保存令牌");
        }

        return new OAuth2AccessTokenAuthenticationToken(
                principal, registeredApp, accessToken, currentRefToken);
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return OAuth2RefreshTokenAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
