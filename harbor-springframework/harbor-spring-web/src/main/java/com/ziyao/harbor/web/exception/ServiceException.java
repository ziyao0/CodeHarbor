package com.ziyao.harbor.web.exception;

import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.core.error.StatusMessage;

import java.io.Serial;

/**
 * @author zhangziyao
 * @since 2023/4/21
 */
public class ServiceException extends RuntimeException implements StatusMessage {
    @Serial
    private static final long serialVersionUID = -3435528093859682944L;


    private final StatusMessage statusMessage;

    public ServiceException() {
        this.statusMessage = Errors.INTERNAL_SERVER_ERROR;
    }

    public ServiceException(StatusMessage StatusMessage) {
        this.statusMessage = StatusMessage;
    }

    public ServiceException(Integer status, String message) {
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
