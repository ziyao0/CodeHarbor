package com.ziyao.harbor.oauth2.client;

import com.ziyao.harbor.oauth2.client.token.AccessTokenRequest;
import com.ziyao.harbor.oauth2.core.OAuth2AccessToken;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public interface OAuth2ClientContext {
    /**
     * @return the current access token if any (may be null or empty)
     */
    OAuth2AccessToken getAccessToken();

    /**
     * @param accessToken the current access token
     */
    void setAccessToken(OAuth2AccessToken accessToken);

    /**
     * @return the current request if any (may be null or empty)
     */
    AccessTokenRequest getAccessTokenRequest();

    /**
     * Convenience method for saving state in the {@link OAuth2ClientContext}.
     *
     * @param stateKey       the key to use to save the state
     * @param preservedState the state to be saved
     */
    void setPreservedState(String stateKey, Object preservedState);

    /**
     * @param stateKey the state key to lookup
     * @return the state preserved with this key (if any)
     */
    Object removePreservedState(String stateKey);
}
