package com.ziyao.harbor.usercenter.authentication.token;

import com.ziyao.harbor.usercenter.authentication.core.Additional;
import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.core.CredentialsContainer;
import com.ziyao.harbor.usercenter.authentication.core.authority.GrantedAuthority;

import java.io.Serial;
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

    private Set<GrantedAuthority> authorities;

    private boolean authenticated;

    private Additional additional;

    public AbstractAuthenticationToken(Set<GrantedAuthority> authorities) {
        if (authorities == null) {
            this.authorities = Set.of();
            return;
        }
        Set<GrantedAuthority> f = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            if (authority != null) f.add(authority);
        }
        this.authorities = Collections.unmodifiableSet(f);
    }

    @Override
    public void eraseCredentials() {

    }
}
