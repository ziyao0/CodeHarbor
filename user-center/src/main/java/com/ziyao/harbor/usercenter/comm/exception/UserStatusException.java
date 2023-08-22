package com.ziyao.harbor.usercenter.comm.exception;

import com.ziyao.harbor.common.api.IMessage;
import com.ziyao.harbor.common.exception.ServiceException;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public class UserStatusException extends ServiceException {
    private static final long serialVersionUID = -9098359654861487617L;

    public UserStatusException(IMessage message) {
        super(message);
    }
}
