package com.ziyao.harbor.usercenter.service.security;

import com.ziyao.harbor.usercenter.authentication.core.Authentication;
import com.ziyao.harbor.usercenter.response.AccessTokenResponse;
import com.ziyao.harbor.usercenter.response.OAuth2AuthorizationCodeResponse;

/**
 * @author ziyao zhang
 * @time 2024/6/15
 */
public interface AuthorizationServer {

    /**
     * 授权
     */
    OAuth2AuthorizationCodeResponse authorize(Long appId, String state, String grantType);

    /**
     * 通过授权码或刷新token获取认证token
     *
     * @param authentication 授权对象
     * @return {@link com.ziyao.security.oauth2.core.OAuth2AccessToken}
     */
    AccessTokenResponse token(Authentication authentication);
}
