package com.ziyao.harbor.usercenter.common.exception;

import lombok.Data;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Data
public abstract class AuthenticateExceptions {


    /**
     * 创建验证密码失败异常
     */
    public static AuthenticateException createValidatedFailure() {
        throw new AuthenticateException(Errors.ERROR_100006);
    }

    private AuthenticateExceptions() {
        throw new IllegalStateException();
    }
}
