package com.ziyao.harbor.usercenter.authenticate;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface AuthenticateDetails {

    Long getAppId();

    String getAccessKey();

    String getSecretKey();

    boolean isAuthenticated();
}
