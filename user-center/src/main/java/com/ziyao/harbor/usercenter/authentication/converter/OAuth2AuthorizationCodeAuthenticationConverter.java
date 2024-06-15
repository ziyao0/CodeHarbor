package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.context.SecurityContextHolder;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2AuthorizationCodeAuthenticationToken;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.error.OAuth2ErrorCodes;
import com.ziyao.security.oauth2.support.OAuth2EndpointUtils;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author ziyao
 * @since 2024/06/12 09:58:14
 */
public class OAuth2AuthorizationCodeAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(AuthenticationRequest request) {

        if (!AuthorizationGrantType.AUTHORIZATION_CODE.matches(request.getGrantType())) {
            return null;
        }

        Authentication appPrincipal = SecurityContextHolder.getContext().getAuthentication();

        String code = request.getCode();

        if (!Strings.hasText(code)) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CODE,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        String redirectUri = request.getRedirectUri();


        return new OAuth2AuthorizationCodeAuthenticationToken(appPrincipal, code, redirectUri);
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (!AuthorizationGrantType.AUTHORIZATION_CODE.matches(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))) {
            return null;
        }

        Authentication appPrincipal = SecurityContextHolder.getContext().getAuthentication();

        String code = request.getParameter(OAuth2ParameterNames.CODE);

        if (!Strings.hasText(code)) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CODE,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        String redirectUri = request.getParameter(OAuth2ParameterNames.REDIRECT_URI);

        return new OAuth2AuthorizationCodeAuthenticationToken(appPrincipal, code, redirectUri);
    }
}
