package com.cfx.usercenter.security.core;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public interface SuccessHandler<T, R> {


    /**
     * 成功时调用该方法
     *
     * @return {@link R}
     */
    R onSuccess(T t);
}
