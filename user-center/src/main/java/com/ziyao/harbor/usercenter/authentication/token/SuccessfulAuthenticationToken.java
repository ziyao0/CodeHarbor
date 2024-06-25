package com.ziyao.harbor.usercenter.authentication.token;

import com.ziyao.security.oauth2.core.GrantedAuthority;
import com.ziyao.security.oauth2.core.RegisteredApp;
import com.ziyao.security.oauth2.core.UserDetails;
import lombok.Getter;

import java.util.Collection;

/**
 * @author ziyao zhang
 * @time 2024/6/18
 */
public class SuccessfulAuthenticationToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = 919449188452752172L;

    private final UserDetails principal;
    @Getter
    private final RegisteredApp registeredApp;

    public SuccessfulAuthenticationToken(UserDetails principal, RegisteredApp registeredApp,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.registeredApp = registeredApp;
    }


    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
