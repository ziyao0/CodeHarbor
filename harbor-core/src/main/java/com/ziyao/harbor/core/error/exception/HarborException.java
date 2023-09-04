package com.ziyao.harbor.core.error.exception;

import com.ziyao.harbor.core.error.IMessage;

import java.io.Serial;

/**
 * @author ziyao
 * @since 2023/4/23
 */

public class HarborException extends RuntimeException implements IMessage {
    @Serial
    private static final long serialVersionUID = -1850478101365902550L;
    private final Integer status;


    public HarborException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public HarborException(IMessage iMessage) {
        super(iMessage.getMessage());
        this.status = iMessage.getStatus();
    }

    public HarborException(Integer status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HarborException(IMessage iMessage, Throwable cause) {
        super(iMessage.getMessage(), cause);
        this.status = iMessage.getStatus();
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
