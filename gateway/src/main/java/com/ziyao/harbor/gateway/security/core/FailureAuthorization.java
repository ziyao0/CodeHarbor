package com.ziyao.harbor.gateway.security.core;

import com.ziyao.harbor.gateway.security.api.Authorization;
import lombok.Data;

/**
 * @author ziyao zhang
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
}
