package com.ziyao.harbor.usercenter.comm.exception;

/**
 * @author ziyao zhang
 * @since 2023/11/10
 */
public abstract class AuthenticatedExceptions {


    /**
     * 创建验证密码失败异常
     */
    public static AuthenticatedException createValidatedFailure() {
        throw new AuthenticatedException(Errors.ERROR_100006);
    }

    private AuthenticatedExceptions() {
        throw new IllegalStateException();
    }
}
