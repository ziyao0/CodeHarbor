package com.ziyao.harbor.usercenter.comm.exception;

import com.ziyao.harbor.core.error.StatusMessage;


/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public class AuthenticatedException extends RuntimeException implements StatusMessage {

    private final StatusMessage statusMessage;

    public AuthenticatedException(StatusMessage StatusMessage) {
        this.statusMessage = StatusMessage;
    }

    public AuthenticatedException(Integer status, String message) {
        this.statusMessage = StatusMessage.getInstance(status, message);
    }

    @Override
    public Integer getStatus() {
        return statusMessage.getStatus();
    }


    @Override
    public String getMessage() {
        return statusMessage.getMessage();
    }
}