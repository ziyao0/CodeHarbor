package com.ziyao.harbor.usercenter.common.exception;

import com.ziyao.harbor.core.error.StatusMessage;
import com.ziyao.security.oauth2.core.AuthenticationException;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @time 2024/6/18
 */
public class AuthenticationFailureException extends AuthenticationException implements StatusMessage {
    @Serial
    private static final long serialVersionUID = 2065959447074513639L;

    private final StatusMessage statusMessage;


    public AuthenticationFailureException(String message) {
        super(message);
        this.statusMessage = StatusMessage.getInstance(401, message);
    }

    public AuthenticationFailureException(int code, String message) {
        super(message);
        this.statusMessage = StatusMessage.getInstance(code, message);
    }

    public AuthenticationFailureException(StatusMessage statusMessage) {
        super(statusMessage.getMessage());
        this.statusMessage = statusMessage;
    }

    @Override
    public Integer getStatus() {
        return statusMessage.getStatus();
    }
}
