package com.ziyao.harbor.usercenter.common.exception;

import com.ziyao.harbor.core.error.StatusMessage;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public enum Errors implements StatusMessage {
    ERROR_100001(100001, "认证失败,账号已锁定"),
    ERROR_100002(100002, "认证失败,用户以禁用"),
    ERROR_100003(100003, "认证失败,账号过期"),
    ERROR_100004(100004, "认证失败,账号凭证过期"),
    ERROR_100005(100005, "认证失败,账号密码错误"),
    ERROR_100006(100006, "认证失败,账号密码错误"),
    ERROR_100007(100007, "当前认证处理器不支持"),
    ERROR_100008(100008, "认证失败，非法认证参数"),
    ERROR_100009(100009, "认证失败,账号密码错误"),

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


    Errors(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
