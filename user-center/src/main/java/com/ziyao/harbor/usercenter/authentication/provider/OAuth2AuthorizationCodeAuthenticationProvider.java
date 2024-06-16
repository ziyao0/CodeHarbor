package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.token.OAuth2AccessTokenAuthenticationToken;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AccessTokenGenerator;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AuthorizationCodeAuthenticationToken;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2TokenGenerator;
import com.ziyao.harbor.usercenter.service.app.RegisteredAppService;
import com.ziyao.harbor.usercenter.service.oauth2.OAuth2AuthorizationService;
import com.ziyao.harbor.web.exception.ServiceException;
import com.ziyao.security.oauth2.context.SecurityContextHolder;
import com.ziyao.security.oauth2.core.*;
import com.ziyao.security.oauth2.token.DefaultOAuth2TokenContext;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Component
public class OAuth2AuthorizationCodeAuthenticationProvider implements AuthenticationProvider {

    private final OAuth2AuthorizationService authorizationService;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private final RegisteredAppService registeredAppService;

    public OAuth2AuthorizationCodeAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                         OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                         RegisteredAppService registeredAppService) {
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.registeredAppService = registeredAppService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        OAuth2AuthorizationCodeAuthenticationToken codeAuthenticationToken = (OAuth2AuthorizationCodeAuthenticationToken) authentication;
        // 授权码
        String code = codeAuthenticationToken.getCode();

        OAuth2Authorization authorization = authorizationService.findByToken(code, new OAuth2TokenType(OAuth2ParameterNames.CODE));
        if (authorization == null) {
            throw new ServiceException();
        }
        Optional<OAuth2Authorization.Token<OAuth2AuthorizationCode>> tokenOptional = Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class));


        if (!tokenOptional.map(OAuth2Authorization.Token::isActive).orElse(false)) {
            // TODO 无效令牌
        }

        // 验证

        RegisteredApp registeredApp = registeredAppService.findById(authorization.getAppId());

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .authorization(authorization)
                .registeredApp(registeredApp);

        DefaultOAuth2TokenContext context1 = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
        OAuth2RefreshToken refreshToken = (OAuth2RefreshToken) this.tokenGenerator.generate(context1);

        DefaultOAuth2TokenContext context2 = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();

        OAuth2AccessTokenGenerator.OAuth2AccessTokenClaims accessToken = (OAuth2AccessTokenGenerator.OAuth2AccessTokenClaims) this.tokenGenerator.generate(context2);


        OAuth2Authorization authorization1 = OAuth2Authorization.from(authorization)
                .token(refreshToken)
                .token(new OAuth2AccessToken(accessToken.getTokenType(), accessToken.getTokenValue(),
                        accessToken.getIssuedAt(), accessToken.getExpiresAt(), accessToken.getScopes()), metadata -> metadata.putAll(accessToken.getClaims()))
                .build();

        authorizationService.save(authorization1);

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        return new OAuth2AccessTokenAuthenticationToken(
                principal, registeredApp, accessToken, refreshToken);
    }


    @Override
    public boolean supports(Class<?> authenticationClass) {
        return OAuth2AuthorizationCodeAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
