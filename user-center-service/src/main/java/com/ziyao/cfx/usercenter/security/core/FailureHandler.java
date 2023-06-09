package com.ziyao.cfx.usercenter.security.core;

import com.ziyao.cfx.usercenter.comm.exception.AuthenticationException;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public interface FailureHandler<T, E extends Exception> {


    /**
     * 失败时调用该方法
     *
     * @throws AuthenticationException 认证异常
     */
    void onFailure(T t, E e);
}
