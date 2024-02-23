package com.ziyao.harbor.gateway.core;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface AuthorizerManager {

    /**
     * 获取授权方
     *
     * @param name 授权方名称
     * @return {@link Authorizer}
     */
    Authorizer getAuthorizer(String name);
}
