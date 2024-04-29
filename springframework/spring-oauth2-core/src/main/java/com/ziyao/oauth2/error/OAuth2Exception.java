package com.ziyao.oauth2.error;



/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
public abstract class OAuth2Exception extends RuntimeException {

    private static final long serialVersionUID = 6313700182260072597L;


    public OAuth2Exception(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2Exception(String msg) {
        super(msg);
    }
}
