package com.cfx.gateway.security.core;

import com.cfx.gateway.security.api.Authorization;
import lombok.Data;

/**
 * @author Eason
 * @since 2023/5/16
 */
@Data
public class SuccessAuthorization implements Authorization {

    private Long appid;
    private Long userId;
    private String username;
    private String token;

    public SuccessAuthorization() {
    }

    public SuccessAuthorization(Long appid, Long userId, String username, String token) {
        this.appid = appid;
        this.userId = userId;
        this.username = username;
        this.token = token;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public boolean isSecurity() {
        return false;
    }

    @Override
    public boolean isAuthorized() {
        return true;
    }
}
