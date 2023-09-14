package com.ziyao.harbor.core.error.exception;

import com.ziyao.harbor.core.error.StatusMessage;

import java.io.Serial;

/**
 * @author ziyao
 * @since 2023/4/23
 */

public class HarborException extends RuntimeException implements StatusMessage {
    @Serial
    private static final long serialVersionUID = -1850478101365902550L;
    private final Integer status;


    public HarborException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public HarborException(StatusMessage statusMessage) {
        super(statusMessage.getMessage());
        this.status = statusMessage.getStatus();
    }

    public HarborException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HarborException(StatusMessage statusMessage, Throwable cause) {
        super(statusMessage.getMessage(), cause);
        this.status = statusMessage.getStatus();
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
