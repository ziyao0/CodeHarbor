package com.ziyao.cfx.usercenter.security.core;

import com.ziyao.cfx.usercenter.security.api.Authentication;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public interface GlobalProcessor<T, R> extends Ordered {

    /**
     * 前置处理器，在 {@link GlobalProcessor#process(T)} 执行前执行
     */
    @Nullable
    default void preProcessBefore(T t) {
    }

    /**
     * 全局处理器
     *
     * @param t 用户认证请求的数据
     * @return 返回处理对象
     */
    @Nullable
    R process(T t);

    /**
     * 后置处理器 在 {@link GlobalProcessor#process(T)} 后执行
     *
     * @param authentication 认证信息
     */
    @Nullable
    default void preProcessAfter(Authentication authentication) {

    }

    default int getOrder() {
        return 0;
    }
}
