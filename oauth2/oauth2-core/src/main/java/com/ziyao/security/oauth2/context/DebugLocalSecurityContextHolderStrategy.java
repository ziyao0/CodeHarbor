package com.ziyao.security.oauth2.context;

import com.ziyao.security.oauth2.core.Additional;
import com.ziyao.security.oauth2.core.Authentication;
import com.ziyao.security.oauth2.core.GrantedAuthority;
import com.ziyao.security.oauth2.core.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @time 2024/6/13
 */
public class DebugLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    @Override
    public void clearContext() {

    }

    @Override
    public AuthenticationContext getContext() {
        return getDeferredContext().get();
    }

    @Override
    public Supplier<AuthenticationContext> getDeferredContext() {
        return () -> {
            AuthenticationContext context = createEmptyContext();
            context.setAuthentication(createAuthenticatedToken());
            return context;
        };
    }

    @Override
    public void setContext(AuthenticationContext context) {

    }

    @Override
    public AuthenticationContext createEmptyContext() {
        return new DefaultAuthenticationContext();
    }

    private Authentication createAuthenticatedToken() {

        UserDetails userDetails = new UserDetails() {
            @Serial
            private static final long serialVersionUID = 3445961897468761978L;

            @Override
            public String getUsername() {
                return "admin";
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }
        };

        return new Authentication() {
            @Serial
            private static final long serialVersionUID = 7615063167518644592L;

            @Override
            public Object getPrincipal() {
                return userDetails;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Additional getAdditional() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean authenticated) {

            }
        };
    }
}
