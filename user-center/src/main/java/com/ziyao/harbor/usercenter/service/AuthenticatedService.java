package com.ziyao.harbor.usercenter.service;

import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import org.springframework.lang.Nullable;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface AuthenticatedService {
    /**
     * 用户登陆功能
     *
     * @param authenticatedRequest 认证请求参数
     * @return 返回认证后信息
     */
    String login(AuthenticatedRequest authenticatedRequest);

    /**
     * 生成授权码
     *
     * @param authenticationRequest 生成授权码请求
     * @return 返回授权码信息
     */
    String generateAuthenticationCode(AuthenticationRequest authenticationRequest);

    OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType);
}
