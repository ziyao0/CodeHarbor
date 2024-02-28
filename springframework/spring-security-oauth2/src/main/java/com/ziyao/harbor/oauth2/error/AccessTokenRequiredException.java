package com.ziyao.harbor.oauth2.error;

import com.ziyao.harbor.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public class AccessTokenRequiredException extends RuntimeException {
    private static final long serialVersionUID = -8477823836061012351L;

    public AccessTokenRequiredException(OAuth2ProtectedResourceDetails resource) {

    }
}
