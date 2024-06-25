package com.ziyao.security.oauth2.core;

import java.io.Serial;

/**
 * @author ziyao
 * @since 2024/06/18 14:28:32
 */
public abstract class AuthenticationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5592880436851278628L;

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}