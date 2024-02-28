package com.ziyao.harbor.oauth2.provider.authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public interface TokenExtractor {
    /**
     * Extract a token value from an incoming request without authentication.
     *
     * @param request the current ServletRequest
     * @return an authentication token whose principal is an access token (or null if there is none)
     */
    Object extract(HttpServletRequest request);
}
