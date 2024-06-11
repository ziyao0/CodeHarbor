package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedUser;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class OAuth2AuthorizationCodeAuthenticator implements OAuth2Authenticator {

    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest) {
        return null;
    }

    @Override
    public AuthorizationGrantType getAuthorizationGrantType() {
        return AuthorizationGrantType.AUTHORIZATION_CODE;
    }
}
