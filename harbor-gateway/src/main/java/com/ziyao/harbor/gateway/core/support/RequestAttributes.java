package com.ziyao.harbor.gateway.core.support;

import com.google.common.collect.ImmutableSet;
import com.ziyao.harbor.core.ParameterNames;

import java.util.Set;

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
    public static final String RESOURCE = ParameterNames.RESOURCE;
    public static final String DIGEST = ParameterNames.DIGEST;


    public static final Set<String> AUTHORIZATION_HEADERS =
            ImmutableSet.of(AUTHORIZATION, TIMESTAMP, REFRESH_TOKEN, DIGEST);
}
