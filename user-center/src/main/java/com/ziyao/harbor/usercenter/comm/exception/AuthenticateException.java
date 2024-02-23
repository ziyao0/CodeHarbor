package com.ziyao.harbor.usercenter.comm.exception;

import com.ziyao.harbor.core.error.StatusMessage;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public class AuthenticateException extends AbstractException {


    private static final long serialVersionUID = -6415837182430157077L;

    public AuthenticateException(StatusMessage StatusMessage) {
        super(StatusMessage);
    }
}
