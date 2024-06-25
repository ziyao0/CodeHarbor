package com.ziyao.harbor.usercenter.authentication.core;

import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.security.oauth2.core.CredentialsContainer;
import com.ziyao.security.oauth2.core.GrantedAuthority;
import com.ziyao.security.oauth2.core.UserDetails;
import lombok.Getter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author ziyao
 * @since 2024/06/11 14:53:31
 */

public class UserInfo implements UserDetails, CredentialsContainer {


    @Serial
    private static final long serialVersionUID = 5236617915182892884L;

    /**
     * 用户id
     */
    @Getter
    private final Long id;

    /**
     * 用户账号
     */
    private final String username;

    /**
     * 昵称
     */
    @Getter
    private final String nickname;

    /**
     * 用户凭证
     */
    private String password;

    /**
     * 账号状态
     */
    private final Integer status;


    private final Collection<? extends GrantedAuthority> authorities;

    public UserInfo(Long id, String username, String nickname,
                    String password, Integer status, Collection<? extends GrantedAuthority> authorities) {
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
        private Integer status;
        private List<GrantedAuthority> authorities = new ArrayList<>();

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

        public UserBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        public UserBuilder authorities(Consumer<Collection<GrantedAuthority>> authoritiesConsumer) {
            authoritiesConsumer.accept(this.authorities);
            return this;
        }

        public UserInfo build() {
            return new UserInfo(id, username, nickname, password, status, authorities);
        }
    }
}
