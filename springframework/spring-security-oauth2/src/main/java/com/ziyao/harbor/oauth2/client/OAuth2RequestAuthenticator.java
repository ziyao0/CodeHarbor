package com.ziyao.harbor.oauth2.client;

import com.ziyao.harbor.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.http.client.ClientHttpRequest;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public interface OAuth2RequestAuthenticator {
    void authenticate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext clientContext, ClientHttpRequest request);

}
