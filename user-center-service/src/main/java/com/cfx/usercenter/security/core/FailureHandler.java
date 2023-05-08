package com.cfx.usercenter.security.core;

/**
 * @author Eason
 * @since 2023/5/8
 */
public interface FailureHandler<T, E extends Exception> {


    /**
     * 失败时调用该方法
     *
     * @throws com.cfx.usercenter.comm.exception.AuthenticationException 认证异常
     */
    void onFailure(T t, E e);
}
