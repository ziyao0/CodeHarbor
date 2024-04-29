package com.ziyao.harbor.gateway.support;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class ParameterNames {

    private ParameterNames() {
    }

    public static final String AUTHORIZATION = "Authorization";

    public static final String TIMESTAMP = "Timestamp";

    public static final String REFRESH_TOKEN = "Refresh";

    public static final String RESOURCE = "Resource";
    public static final String DIGEST = "Digest";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";


}
