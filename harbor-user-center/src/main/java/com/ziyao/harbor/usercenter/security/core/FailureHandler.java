package com.ziyao.harbor.usercenter.security.core;

import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedException;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public interface FailureHandler<T, E extends Exception> {


    /**
     * 失败时调用该方法
     *
     * @throws AuthenticatedException 认证异常
     */
    void onFailure(T t, E e);
}
