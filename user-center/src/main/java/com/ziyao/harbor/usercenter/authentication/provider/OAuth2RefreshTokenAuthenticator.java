package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class OAuth2RefreshTokenAuthenticator implements OAuth2Authenticator {


    @Override
    public Authentication authenticate(Authentication authentication) {
        return null;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return false;
    }
}
