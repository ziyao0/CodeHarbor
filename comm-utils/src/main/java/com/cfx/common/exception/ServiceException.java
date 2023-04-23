package com.cfx.common.exception;

import com.cfx.common.api.IMessage;
import com.cfx.common.writer.Errors;

/**
 * @author Eason
 * @date 2023/4/21
 */
public class ServiceException extends RuntimeException implements IMessage {
    private static final long serialVersionUID = -3435528093859682944L;


    private final IMessage message;

    public ServiceException() {
        this.message = Errors.INTERNAL_SERVER_ERROR;
    }

    public ServiceException(IMessage IMessage) {
        this.message = IMessage;
    }

    public ServiceException(Integer status, String message) {
        this.message = IMessage.getInstance(status, message);
    }

    @Override
    public Integer getStatus() {
        return message.getStatus();
    }


}
