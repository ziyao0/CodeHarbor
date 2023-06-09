package com.ziyao.cfx.gateway.security.api;

import java.io.Serializable;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface Authorization extends Serializable {
    String getToken();

    default boolean isSecurity() {
        return false;
    }

    default boolean isAuthorized() {
        return false;
    }

    default String getMessage() {
        return null;
    }

}