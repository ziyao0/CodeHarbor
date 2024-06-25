package com.ziyao.security.oauth2.core.token;

import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author ziyao zhang
 * @since 2024/3/27
 */
public abstract class BearerTokenExtractor {

    private final static Log logger = LogFactory.getLog(BearerTokenExtractor.class);

    public String extract(ServerHttpRequest request) {
        return this.extractForHeaders(request);
    }

    /**
     * Extract the OAuth bearer token from a header.
     *
     * @param request The request.
     * @return The token, or null if no OAuth authorization header was supplied.
     */
    protected String extractForHeaders(ServerHttpRequest request) {
        String value = request.getHeaders().getFirst("Authorization");
        if (!Strings.isEmpty(value)) {
            if ((value.toLowerCase().startsWith(TokenType.Bearer.getType().toLowerCase()))) {
                String authHeaderValue = value.substring(TokenType.Bearer.getType().length()).trim();
                // Add this here for the auth details later. Would be better to change the signature of this method.
                request.mutate().header(OAuth2TokenType.ACCESS_TOKEN.getValue(),
                        value.substring(0, TokenType.Bearer.getType().length()).trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        return null;
    }
}
