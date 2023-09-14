package com.ziyao.harbor.web.exception;

import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.core.error.StatusMessage;

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
        this.status = Errors.UNAUTHORIZED.getStatus();
        this.message = Errors.UNAUTHORIZED.getMessage();
    }

    public UnauthorizedException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public UnauthorizedException(String message) {
        this.status = Errors.UNAUTHORIZED.getStatus();
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
