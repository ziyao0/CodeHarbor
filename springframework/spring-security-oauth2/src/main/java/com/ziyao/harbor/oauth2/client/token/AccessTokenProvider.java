//package com.ziyao.harbor.oauth2.client.token;
//
//import com.ziyao.harbor.oauth2.client.resource.OAuth2ProtectedResourceDetails;
//import com.ziyao.harbor.oauth2.core.OAuth2AccessToken;
//import com.ziyao.harbor.oauth2.core.OAuth2RefreshToken;
//
///**
// * @author ziyao zhang
// * @since 2024/2/27
// */
//public interface AccessTokenProvider {
//
//    /**
//     * Obtain a new access token for the specified protected resource.
//     *
//     * @param details    The protected resource for which this provider is to obtain an access token.
//     * @param parameters The parameters of the request giving context for the token details if any.
//     * @return The access token for the specified protected resource. The return value may NOT be null.
//     */
//    OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest parameters);
//
//    /**
//     * Whether this provider supports the specified resource.
//     *
//     * @param resource The resource.
//     * @return Whether this provider supports the specified resource.
//     */
//    boolean supportsResource(OAuth2ProtectedResourceDetails resource);
//
//    /**
//     * @param resource     the resource for which a token refresh is required
//     * @param refreshToken the refresh token to send
//     * @return an access token
//     */
//    OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource,
//                                         OAuth2RefreshToken refreshToken, AccessTokenRequest request);
//
//    /**
//     * @param resource The resource to check
//     * @return true if this provider can refresh an access token
//     */
//    boolean supportsRefresh(OAuth2ProtectedResourceDetails resource);
//}
