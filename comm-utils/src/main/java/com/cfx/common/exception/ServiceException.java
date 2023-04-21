package com.cfx.common.exception;

import com.cfx.common.api.MessageDetails;
import com.cfx.common.writer.Errors;

/**
 * @author Eason
 * @date 2023/4/21
 */
public class ServiceException extends RuntimeException implements MessageDetails {
    private static final long serialVersionUID = -3435528093859682944L;


    private final MessageDetails messageDetails;

    public ServiceException() {
        this.messageDetails = Errors.INTERNAL_SERVER_ERROR;
    }

    public ServiceException(MessageDetails messageDetails) {
        this.messageDetails = messageDetails;
    }

    public ServiceException(Integer status, String message) {
        this.messageDetails = MessageDetails.getInstance(status, message);
    }

    @Override
    public Integer getStatus() {
        return messageDetails.getStatus();
    }


}
