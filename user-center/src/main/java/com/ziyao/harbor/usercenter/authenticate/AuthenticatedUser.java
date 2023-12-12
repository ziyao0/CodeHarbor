package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.authenticate.core.IResource;
import lombok.Data;

import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2023/9/26
 */
@Data
public class AuthenticatedUser {

    private UserDetails user;

    private Set<? extends IResource> resources;

    private boolean authenticated;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UserDetails user;

        public Builder user(UserDetails user) {
            this.user = user;
            return this;
        }

        public AuthenticatedUser build() {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            authenticatedUser.setUser(this.user);
            return authenticatedUser;
        }
    }

}
