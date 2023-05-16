package com.cfx.gateway.security.core;

import com.cfx.gateway.security.api.Authorization;
import lombok.Data;

/**
 * @author Eason
 * @since 2023/5/16
 */
@Data
public class FailureAuthorization implements Authorization {

    public FailureAuthorization() {
    }

    public FailureAuthorization(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public boolean isSecurity() {
        return false;
    }

    @Override
    public boolean isAuthorized() {
        return false;
    }
}
