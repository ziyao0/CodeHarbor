package com.cfx.usercenter.comm.exception;

import com.cfx.common.api.IMessage;
import com.cfx.common.exception.ServiceException;

/**
 * @author Eason
 * @date 2023/4/24
 */
public class UserStatusException extends ServiceException {
    private static final long serialVersionUID = -9098359654861487617L;

    public UserStatusException(IMessage message) {
        super(message);
    }
}
