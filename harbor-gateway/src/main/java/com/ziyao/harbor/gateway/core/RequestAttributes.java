package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.ParameterNames;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class RequestAttributes {

    private RequestAttributes() {
    }

    public static final String AUTHORIZATION = ParameterNames.AUTHORIZATION;

    public static final String TIMESTAMP = ParameterNames.TIMESTAMP;

    public static final String REFRESH_TOKEN = ParameterNames.REFRESH_TOKEN;

}
