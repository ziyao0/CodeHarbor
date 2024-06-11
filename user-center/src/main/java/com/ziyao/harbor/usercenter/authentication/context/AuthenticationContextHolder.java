package com.ziyao.harbor.usercenter.authentication.context;

import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public class AuthenticationContextHolder {

    private static AuthenticationContextHolderStrategy strategy;

    private static int initializeCount = 0;

    static {
        initialize();
    }

    private static void initialize() {
        initializeStrategy();
        initializeCount++;
    }

    private static void initializeStrategy() {
        // 默认使用当前线程
        strategy = new ThreadLocalAuthenticationContextHolderStrategy();
    }


    public static void clearContext() {
        strategy.clearContext();
    }


    public static AuthenticationContext getContext() {
        return strategy.getContext();
    }


    public static Supplier<AuthenticationContext> getDeferredContext() {
        return strategy.getDeferredContext();
    }


    public static void setContext(AuthenticationContext context) {
        strategy.setContext(context);
    }


    public static void setDeferredContext(Supplier<AuthenticationContext> deferredContext) {
        strategy.setDeferredContext(deferredContext);
    }

    public static AuthenticationContext createEmptyContext() {
        return strategy.createEmptyContext();
    }


}
