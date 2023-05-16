package com.cfx.gateway.security.api;

import java.io.Serializable;

/**
 * @author Eason
 * @since 2023/5/16
 */
public interface Authorization extends Serializable {
    String getToken();

    boolean isSecurity();

    boolean isAuthorized();

}