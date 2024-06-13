package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AuthorizationCodeAuthenticationToken;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.DefaultOAuth2TokenContext;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.generator.OAuth2TokenGenerator;
import com.ziyao.harbor.usercenter.service.oauth2.OAuth2AuthorizationService;
import com.ziyao.harbor.web.exception.ServiceException;
import com.ziyao.security.oauth2.core.*;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class OAuth2AuthorizationCodeAuthenticator implements OAuth2Authenticator {

    private final OAuth2AuthorizationService authorizationService;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public OAuth2AuthorizationCodeAuthenticator(OAuth2AuthorizationService authorizationService,
                                                OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        OAuth2AuthorizationCodeAuthenticationToken codeAuthenticationToken = (OAuth2AuthorizationCodeAuthenticationToken) authentication;

        String code = codeAuthenticationToken.getCode();

        OAuth2Authorization authorization = authorizationService.findByToken(code, new OAuth2TokenType(OAuth2ParameterNames.CODE));
        if (authorization == null) {
            throw new ServiceException();
        }
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorization.getToken(OAuth2AuthorizationCode.class);

        // 验证
        Authentication principal = (Authentication) codeAuthenticationToken.getPrincipal();


        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .authorization(authorization)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .principal(principal);
//                .registeredApp()

        DefaultOAuth2TokenContext tokenContext = tokenContextBuilder.build();
        OAuth2Token oAuth2Token = this.tokenGenerator.generate(tokenContext);

        return null;
    }


    @Override
    public boolean supports(Class<?> authenticationClass) {
        return OAuth2AuthorizationCodeAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
