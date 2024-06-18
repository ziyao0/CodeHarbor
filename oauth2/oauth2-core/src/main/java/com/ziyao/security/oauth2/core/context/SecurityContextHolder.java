package com.ziyao.security.oauth2.core.context;

import com.ziyao.harbor.core.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author ziyao zhang
 * @time 2024/6/11
 */
public abstract class SecurityContextHolder {

    private static final Logger log = LoggerFactory.getLogger(SecurityContextHolder.class);

    public static final String MODE_THREAD_LOCAL = "MODE_THREAD_LOCAL";

    public static final String MODE_DEBUG = "MODE_DEBUG";

    public static final String SYSTEM_PROPERTY = "security.strategy";

    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);

    private static SecurityContextHolderStrategy strategy;

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

        if (!Strings.hasText(strategyName)) {
            strategyName = MODE_THREAD_LOCAL;
        }

        if (MODE_THREAD_LOCAL.equals(strategyName)) {
            strategy = new ThreadLocalSecurityContextHolderStrategy();
            return;
        }

        if (MODE_DEBUG.equals(strategyName)) {
            strategy = new DebugLocalSecurityContextHolderStrategy();
            return;
        }
        log.error("未知的策略名称：{}", strategyName);
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

    public static boolean isAuthorized() {
        return strategy.isAuthentication();
    }

    public static boolean isUnauthorized() {
        return !strategy.isAuthentication();
    }

    public SecurityContextHolder() {
    }

}
