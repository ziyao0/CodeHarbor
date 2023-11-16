package com.ziyao.harbor.usercenter.comm.exception;

import lombok.Data;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Data
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
