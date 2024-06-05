package com.ziyao.security.oauth2.error;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public abstract class OAuth2Exception extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6313700182260072597L;


    public OAuth2Exception(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2Exception(String msg) {
        super(msg);
    }
}
