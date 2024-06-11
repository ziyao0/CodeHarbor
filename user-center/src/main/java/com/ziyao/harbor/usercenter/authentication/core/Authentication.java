package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.harbor.usercenter.authentication.core.authority.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/10 16:08:13
 */
public interface Authentication extends Serializable {

    String getUsername();

    String getCredentials();

    Set<? extends GrantedAuthority> getAuthorities();

    Additional getAdditional();

    boolean isAuthenticated();

    void setAuthenticated(boolean authenticated);
}
