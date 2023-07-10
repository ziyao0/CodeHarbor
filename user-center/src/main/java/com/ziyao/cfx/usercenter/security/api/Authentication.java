package com.ziyao.cfx.usercenter.security.api;

/**
 * @author zhangziyao
 * @since 2023/4/24
 */
public interface Authentication {

    Long getAppId();

    String getAccessKey();

    String getSecretKey();

    boolean isAuthenticated();

    default String getProviderName() {
        return null;
    }
}
