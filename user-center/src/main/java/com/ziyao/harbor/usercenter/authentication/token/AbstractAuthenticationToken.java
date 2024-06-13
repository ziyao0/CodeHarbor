package com.ziyao.harbor.usercenter.authentication.token;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authentication.core.Additional;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.core.CredentialsContainer;
import com.ziyao.harbor.usercenter.authentication.core.UserDetails;
import com.ziyao.harbor.usercenter.authentication.core.authority.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/11 17:21:03
 */
public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer {
    @Serial
    private static final long serialVersionUID = 6449672296011987802L;

    private final Collection<? extends GrantedAuthority> authorities;

    private boolean authenticated;

    private Additional additional;

    public AbstractAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null) {
            this.authorities = Set.of();
            return;
        }
        Collection<GrantedAuthority> f = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            if (authority != null) f.add(authority);
        }
        this.authorities = Collections.unmodifiableCollection(f);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public Additional getAdditional() {
        return this.additional;
    }

    public String getName() {
        if (getPrincipal() == null) {
            return Strings.EMPTY;
        }
        if (getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return getPrincipal().toString();
    }

    @Override
    public void eraseCredentials() {
        if (getCredentials() instanceof CredentialsContainer credentialsContainer) {
            credentialsContainer.eraseCredentials();
        }
    }
}