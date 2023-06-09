package com.ziyao.cfx.common.exception;

import com.ziyao.cfx.common.api.IMessage;
import com.ziyao.cfx.common.writer.Errors;

import java.io.Serial;

/**
 * @author zhangziyao
 * @date 2023/4/21
 */
public class UnauthorizedException extends RuntimeException implements IMessage {
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
        this.status = Errors.E_401;
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
