package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.security.oauth2.core.GrantedAuthority;
import com.ziyao.security.oauth2.core.Permission;
import lombok.Getter;

import java.io.Serial;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/11 11:07:16
 */
public class OAuth2UserAuthority implements GrantedAuthority {


    @Serial
    private static final long serialVersionUID = -9137508417727958078L;

    private final String authority;

    @Getter
    private final Map<String, Object> attributes;

    public OAuth2UserAuthority(Map<String, Object> attributes) {
        this("OAUTH2_USER", attributes);
    }

    public OAuth2UserAuthority(String authority, Map<String, Object> attributes) {
        this.authority = authority;
        this.attributes = attributes;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public Set<Permission> getPermissions() {
        return Set.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2UserAuthority that = (OAuth2UserAuthority) o;
        return Objects.equals(authority, that.authority) && Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority, attributes);
    }
}
