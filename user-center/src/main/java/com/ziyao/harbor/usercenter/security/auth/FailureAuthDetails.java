package com.ziyao.harbor.usercenter.security.auth;

import com.ziyao.harbor.common.api.IMessage;
import com.ziyao.harbor.common.writer.ResponseBuilder;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import lombok.Data;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Data
public class FailureAuthDetails implements Authentication {


    private IMessage message;


    public FailureAuthDetails(IMessage message) {
        this.message = message;
    }

    public FailureAuthDetails(Integer status, String message) {
        this.message = ResponseBuilder.failed(status, message);
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

}
