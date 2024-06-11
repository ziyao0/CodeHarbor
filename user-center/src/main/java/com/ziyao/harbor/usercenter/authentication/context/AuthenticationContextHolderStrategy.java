package com.ziyao.harbor.usercenter.authentication.context;

import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public interface AuthenticationContextHolderStrategy {

    /**
     * 清理当前上下文
     */
    void clearContext();


    AuthenticationContext getContext();


    default Supplier<AuthenticationContext> getDeferredContext() {
        return this::getContext;
    }


    void setContext(AuthenticationContext context);


    default void setDeferredContext(Supplier<AuthenticationContext> deferredContext) {
        setContext(deferredContext.get());
    }

    AuthenticationContext createEmptyContext();
}
