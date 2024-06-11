package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.harbor.usercenter.authentication.core.authority.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author ziyao
 * @since 2024/06/10 16:08:13
 */
public interface Authentication extends Serializable {

    Object getPrincipal();

    Object getCredentials();

    Collection<? extends GrantedAuthority> getAuthorities();

    Additional getAdditional();

    boolean isAuthenticated();

    void setAuthenticated(boolean authenticated);
}
