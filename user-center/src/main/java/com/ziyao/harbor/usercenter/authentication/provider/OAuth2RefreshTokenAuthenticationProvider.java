package com.ziyao.harbor.usercenter.authentication.provider;

import com.ziyao.security.oauth2.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Component
public class OAuth2RefreshTokenAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) {
        return null;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return false;
    }
}
