package com.cfx.usercenter.security.auth;

import com.cfx.common.api.IMessage;
import com.cfx.usercenter.security.api.Authentication;
import lombok.Data;

/**
 * @author Eason
 * @since 2023/5/8
 */
@Data
public class FailureAuthDetails implements Authentication {


    private IMessage message;


    public FailureAuthDetails(IMessage message) {
        this.message = message;
    }

    @Override
    public Long getAppId() {
        return null;
    }

    @Override
    public String getAccessKey() {
        return null;
    }

    @Override
    public String getSecretKey() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public String getProviderName() {
        return null;
    }
}
