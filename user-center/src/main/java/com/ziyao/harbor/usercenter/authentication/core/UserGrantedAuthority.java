package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.security.oauth2.core.GrantedAuthority;
import com.ziyao.security.oauth2.core.Permission;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/11 11:02:23
 */
public class UserGrantedAuthority implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1944660992125502463L;

    private final String role;

    private final Collection<Permission> permissions;

    public UserGrantedAuthority(String role) {
        this(role, Set.of());
    }

    public UserGrantedAuthority(String role, Collection<Permission> permissions) {
        Assert.hasText(role, "role must not be empty");
        this.role = role;
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    @Override
    public Collection<Permission> getPermissions() {
        return this.permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGrantedAuthority that = (UserGrantedAuthority) o;
        return Objects.equals(role, that.role) && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, permissions);
    }
}
