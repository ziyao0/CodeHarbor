package com.ziyao.harbor.usercenter.authentication.token;

import com.google.common.collect.Lists;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.security.oauth2.core.Authentication;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public class OAuth2AuthorizationGrantAuthenticationToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = -2867200150211134714L;

    private final AuthorizationGrantType authorizationGrantType;

    private final Authentication appPrincipal;

    public OAuth2AuthorizationGrantAuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication appPrincipal) {
        super(Lists.newArrayList());
        this.authorizationGrantType = authorizationGrantType;
        this.appPrincipal = appPrincipal;
    }

    @Override
    public Object getPrincipal() {
        return appPrincipal;
    }

    @Override
    public Object getCredentials() {
        return Strings.EMPTY;
    }

    public AuthorizationGrantType getGrantType() {
        return this.authorizationGrantType;
    }
}
