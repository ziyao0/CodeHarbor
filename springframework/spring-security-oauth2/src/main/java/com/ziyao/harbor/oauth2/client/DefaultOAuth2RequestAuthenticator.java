package com.ziyao.harbor.oauth2.client;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import com.ziyao.harbor.oauth2.core.OAuth2AccessToken;
import com.ziyao.harbor.oauth2.error.AccessTokenRequiredException;
import com.ziyao.harbor.oauth2.support.OAuth2Utils;
import org.springframework.http.client.ClientHttpRequest;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public class DefaultOAuth2RequestAuthenticator implements OAuth2RequestAuthenticator {
    @Override
    public void authenticate(OAuth2ProtectedResourceDetails resource,
                             OAuth2ClientContext clientContext, ClientHttpRequest request) {
        OAuth2AccessToken accessToken = clientContext.getAccessToken();
        if (accessToken == null) {
            throw new AccessTokenRequiredException(resource);
        }
        String tokenType = accessToken.getTokenType();
        if (!Strings.hasText(tokenType)) {
            tokenType = OAuth2AccessToken.BEARER_TYPE; // we'll assume basic bearer token type if none is specified.
        } else if (tokenType.equalsIgnoreCase(OAuth2AccessToken.BEARER_TYPE)) {
            // gh-1346
            tokenType = OAuth2AccessToken.BEARER_TYPE; // Ensure we use the correct syntax for the "Bearer" authentication scheme
        }
        request.getHeaders().set(OAuth2Utils.AUTHORIZATION,
                String.format("%s %s", tokenType, accessToken.getValue()));
    }
}
