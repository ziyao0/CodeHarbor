package com.ziyao.security.oauth2.core.context;

import com.ziyao.security.oauth2.core.Authentication;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public class DefaultAuthenticationContext implements AuthenticationContext {
    @Serial
    private static final long serialVersionUID = -6481012224001521435L;

    private Authentication authentication;

    @Override
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
