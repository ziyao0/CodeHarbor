package com.ziyao.harbor.usercenter.comm.exception;

import com.ziyao.harbor.core.error.IMessage;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public enum ErrorsIMessage implements IMessage {

    ACCOUNT_STATUS_LOCKED(100001, "认证失败,账号已锁定"),
    ACCOUNT_STATUS_DISABLED(100002, "认证失败,用户以禁用"),
    ACCOUNT_STATUS_EXPIRED(100003, "认证失败,账号过期"),
    ACCOUNT_STATUS_CREDENTIALS_EXPIRED(100004, "认证失败,账号凭证过期"),
    ACCOUNT_NULL(100005, "认证失败,账号密码错误"),
    ACCOUNT_PD_NULL(100006, "认证失败,账号密码错误"),
    UN_SUPPORTS(100007, "当前认证处理器不支持"),

    ;


    private final Integer status;

    private final String message;

    @Override
    public Integer getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


    ErrorsIMessage(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
