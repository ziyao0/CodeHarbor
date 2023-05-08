package com.cfx.usercenter.security.api;

/**
 * @author zhangziyao
 * @date 2023/4/24
 */
public interface Authentication {

    Long getAppId();

    String getAccessKey();

    String getSecretKey();

    boolean isAuthenticated();

    String getProviderName();
}
