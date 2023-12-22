package com.ziyao.harbor.usercenter.comm.exception;

import com.ziyao.harbor.core.error.StatusMessage;

import java.io.Serial;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public abstract class AbstractException extends RuntimeException implements StatusMessage {

    @Serial
    private static final long serialVersionUID = 1425373540127776483L;
    private final StatusMessage statusMessage;

    public AbstractException(StatusMessage StatusMessage) {
        this.statusMessage = StatusMessage;
    }

    public AbstractException(Integer status, String message) {
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
