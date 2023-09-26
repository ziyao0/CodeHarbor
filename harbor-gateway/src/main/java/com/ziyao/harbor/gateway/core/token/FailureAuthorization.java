package com.ziyao.harbor.gateway.core.token;

import com.ziyao.harbor.gateway.core.token.Authorization;
import lombok.Data;

import java.io.Serial;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
@Data
public class FailureAuthorization implements Authorization {

    @Serial
    private static final long serialVersionUID = 8104427680804234299L;

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
    public String getName() {
        return null;
    }
}
