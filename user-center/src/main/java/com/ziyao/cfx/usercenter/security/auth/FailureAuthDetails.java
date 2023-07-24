package com.ziyao.cfx.usercenter.security.auth;

import com.ziyao.cfx.common.api.IMessage;
import com.ziyao.cfx.common.writer.WebResponseBuilder;
import com.ziyao.cfx.usercenter.security.api.Authentication;
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
        this.message = WebResponseBuilder.failed(status, message);
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
