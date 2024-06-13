package com.ziyao.harbor.usercenter.authentication.context;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.authentication.core.SimpleUser;
import com.ziyao.harbor.usercenter.authentication.core.authority.SimpleUserAuthority;
import com.ziyao.harbor.usercenter.authentication.token.UsernamePasswordAuthenticationToken;

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
        SimpleUser user = SimpleUser.withId(100001L)
                .username("admin")
                .nickname("小张")
                .status((byte) 1)
                .authorities(authorities -> authorities.add(new SimpleUserAuthority("ADMIN")))
                .build();
        return UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
    }
}
