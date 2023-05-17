package com.cfx.common.exception;

import com.cfx.common.api.IMessage;
import com.cfx.common.writer.Errors;

import java.io.Serial;

/**
 * @author zhangziyao
 * @date 2023/4/21
 */
public class ServiceException extends RuntimeException implements IMessage {
    @Serial
    private static final long serialVersionUID = -3435528093859682944L;


    private final IMessage iMessage;

    public ServiceException() {
        this.iMessage = Errors.INTERNAL_SERVER_ERROR;
    }

    public ServiceException(IMessage IMessage) {
        this.iMessage = IMessage;
    }

    public ServiceException(Integer status, String message) {
        this.iMessage = IMessage.getInstance(status, message);
    }

    @Override
    public Integer getStatus() {
        return iMessage.getStatus();
    }


    @Override
    public String getMessage() {
        return iMessage.getMessage();
    }
}
