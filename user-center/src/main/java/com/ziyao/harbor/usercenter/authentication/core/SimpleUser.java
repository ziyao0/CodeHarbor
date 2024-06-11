package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.harbor.usercenter.authentication.core.authority.GrantedAuthority;
import com.ziyao.harbor.usercenter.entity.User;
import lombok.Getter;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author ziyao
 * @since 2024/06/11 14:53:31
 */

public class SimpleUser implements UserDetails, CredentialsContainer {


    @Serial
    private static final long serialVersionUID = 5236617915182892884L;

    /**
     * 用户id
     */
    @Getter
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 昵称
     */
    @Getter
    private String nickname;

    /**
     * 用户凭证
     */
    private String password;

    /**
     * 账号状态
     */
    private Byte status;


    private Set<GrantedAuthority> authorities;

    public SimpleUser(Long id, String username, String nickname, String password, Byte status, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (this.status == null) {
            return true;
        }
        return this.status == 2;
    }

    //账号状态 0正常 1锁定 2过期 3未启用
    @Override
    public boolean isAccountNonLocked() {
        if (this.status == null) {
            return true;
        }
        return this.status == 1;
    }

    /**
     * 密码是否未过期
     *
     * @return false未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (this.status == null) {
            return true;
        }
        return this.status == 3;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    public static UserBuilder from(final User user) {
        return withId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .status(user.getStatus());
    }

    public static UserBuilder withId(Long id) {
        return new UserBuilder().id(id);
    }

    public static UserBuilder withUsername(String username) {
        return new UserBuilder().username(username);
    }

    public static UserBuilder withUserDetails(final UserDetails userDetails) {
        return new UserBuilder().username(userDetails.getUsername()).password(userDetails.getPassword());
    }

    public static final class UserBuilder {
        private Long id;
        private String username;
        private String nickname;
        private String password;
        private Byte status;
        private Set<GrantedAuthority> authorities;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder status(Byte status) {
            this.status = status;
            return this;
        }

        public UserBuilder authorities(Set<GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserBuilder authorities(Consumer<Set<GrantedAuthority>> authoritiesConsumer) {
            authoritiesConsumer.accept(this.authorities);
            return this;
        }

        public SimpleUser build() {
            return new SimpleUser(id, username, nickname, password, status, authorities);
        }
    }
}
