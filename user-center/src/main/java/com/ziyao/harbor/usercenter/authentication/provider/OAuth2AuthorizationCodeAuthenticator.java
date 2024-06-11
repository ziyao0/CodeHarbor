package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AuthorizationCodeAuthenticationToken;
import com.ziyao.harbor.usercenter.service.oauth2.OAuth2AuthorizationService;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2AuthorizationCode;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class OAuth2AuthorizationCodeAuthenticator implements OAuth2Authenticator {

    private final OAuth2AuthorizationService authorizationService;

    public OAuth2AuthorizationCodeAuthenticator(OAuth2AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        OAuth2AuthorizationCodeAuthenticationToken codeAuthenticationToken = (OAuth2AuthorizationCodeAuthenticationToken) authentication;

        String code = codeAuthenticationToken.getCode();

        OAuth2Authorization authorization = authorizationService.findByToken(code, new OAuth2TokenType(OAuth2ParameterNames.CODE));

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationToken = authorization.getToken(OAuth2AuthorizationCode.class);

        // 验证

        return null;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return OAuth2AuthorizationCodeAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
