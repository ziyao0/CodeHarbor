package com.ziyao.harbor.usercenter.authentication.context;

import com.ziyao.harbor.core.utils.Assert;

import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public class ThreadLocalAuthenticationContextHolderStrategy implements AuthenticationContextHolderStrategy {

    private static final ThreadLocal<Supplier<AuthenticationContext>> contextHolder = new ThreadLocal<>();


    @Override
    public void clearContext() {
        contextHolder.remove();
    }

    @Override
    public AuthenticationContext getContext() {
        return getDeferredContext().get();
    }

    @Override
    public Supplier<AuthenticationContext> getDeferredContext() {
        Supplier<AuthenticationContext> result = contextHolder.get();
        if (result == null) {
            AuthenticationContext context = createEmptyContext();
            result = () -> context;
            contextHolder.set(result);
        }
        return result;
    }

    @Override
    public void setContext(AuthenticationContext context) {
        contextHolder.set(() -> context);
    }

    @Override
    public void setDeferredContext(Supplier<AuthenticationContext> deferredContext) {
        Supplier<AuthenticationContext> notNullDeferredContext = () -> {
            AuthenticationContext authenticationContext = deferredContext.get();
            Assert.notNull(authenticationContext, "传入的请求上下文不能为空");
            return authenticationContext;
        };
        contextHolder.set(notNullDeferredContext);
    }

    @Override
    public AuthenticationContext createEmptyContext() {
        return new DefaultAuthenticationContext();
    }
}
