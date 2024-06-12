package com.ziyao.harbor.usercenter.authentication.converter;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.context.AuthenticationContextHolder;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.token.OAuth2RefreshTokenAuthenticationToken;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import com.ziyao.security.oauth2.error.OAuth2ErrorCodes;
import com.ziyao.security.oauth2.support.OAuth2EndpointUtils;
import com.ziyao.security.oauth2.token.OAuth2ParameterNames;

import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/12 10:27:54
 */
public class OAuth2RefreshTokenAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(AuthenticationRequest request) {

        if (!AuthorizationGrantType.REFRESH_TOKEN.isMatches(request.getGrantType())) {
            return null;
        }

        String refreshToken = request.getRefreshToken();

        if (!Strings.hasText(refreshToken)) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REFRESH_TOKEN,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Authentication authentication = AuthenticationContextHolder.getContext().getAuthentication();

        Set<String> scopes = request.getScopes();

        return new OAuth2RefreshTokenAuthenticationToken(refreshToken, authentication, scopes);
    }
}
