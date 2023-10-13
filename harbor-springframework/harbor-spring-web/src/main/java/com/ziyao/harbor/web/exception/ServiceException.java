package com.ziyao.harbor.web.exception;

import com.ziyao.harbor.core.error.StatusMessage;

/**
 * @author zhangziyao
 * @since 2023/4/21
 */
public class ServiceException extends RuntimeException implements StatusMessage {
    private static final long serialVersionUID = -3435528093859682944L;


    private final StatusMessage statusMessage;

    public ServiceException() {
        // TODO: 2023/9/24 业务异常
        this.statusMessage = null;
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
