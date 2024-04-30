package com.ziyao.harbor.web.exception;

import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.harbor.core.error.exception.HarborException;

import java.io.Serial;

/**
 * @author zhangziyao
 * @since 2023/4/21
 */
public class UnauthorizedException extends RuntimeException implements StatusMessage {
    @Serial
    private static final long serialVersionUID = 1350454124169036151L;


    private int status;

    private String message;

    public UnauthorizedException() {
        HarborException unauthorizedException = Exceptions.createUnauthorizedException(null);
        this.status = unauthorizedException.getStatus();
        this.message = unauthorizedException.getMessage();
    }

    public UnauthorizedException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public UnauthorizedException(String message) {
        this.status = 403;
        this.message = message;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
