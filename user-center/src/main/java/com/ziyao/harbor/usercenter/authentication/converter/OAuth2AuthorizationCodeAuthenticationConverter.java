package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.context.AuthenticationContextHolder;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AuthorizationCodeAuthenticationToken;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.error.OAuth2ErrorCodes;
import com.ziyao.security.oauth2.support.OAuth2EndpointUtils;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;

/**
 * @author ziyao
 * @since 2024/06/12 09:58:14
 */
public class OAuth2AuthorizationCodeAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(AuthenticationRequest request) {

        if (!AuthorizationGrantType.AUTHORIZATION_CODE.isMatches(request.getGrantType())) {
            return null;
        }

        Authentication appPrincipal = AuthenticationContextHolder.getContext().getAuthentication();

        String code = request.getCode();

        if (!Strings.hasText(code)) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CODE,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        String redirectUri = request.getRedirectUri();


        return new OAuth2AuthorizationCodeAuthenticationToken(appPrincipal, code, redirectUri);
    }
}
