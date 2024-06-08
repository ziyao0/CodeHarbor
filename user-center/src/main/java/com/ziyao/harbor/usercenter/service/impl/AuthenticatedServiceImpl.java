package com.ziyao.harbor.usercenter.service.impl;

import com.ziyao.harbor.usercenter.authenticate.AuthenticatedHandler;
import com.ziyao.harbor.usercenter.authenticate.AuthenticatorManager;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authenticate.token.DefaultOAuth2TokenContext;
import com.ziyao.harbor.usercenter.authenticate.token.generator.OAuth2TokenGenerator;
import com.ziyao.harbor.usercenter.common.exception.AuthenticateException;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.harbor.usercenter.service.AuthenticatedService;
import com.ziyao.security.oauth2.core.OAuth2Authorization;
import com.ziyao.security.oauth2.core.OAuth2AuthorizationCode;
import com.ziyao.security.oauth2.core.OAuth2Token;
import com.ziyao.security.oauth2.core.OAuth2TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Service
public class AuthenticatedServiceImpl implements AuthenticatedService {

    private final AuthenticatorManager authenticatorManager;
    private final AuthenticatedHandler authenticatedHandler;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public AuthenticatedServiceImpl(AuthenticatorManager authenticatorManager,
                                    AuthenticatedHandler authenticatedHandler,
                                    OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.authenticatorManager = authenticatorManager;
        this.authenticatedHandler = authenticatedHandler;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public String login(AuthenticatedRequest request) {
        try {
            preprocess(request);
            AuthenticatedUser authenticate = authenticatorManager.authenticate(request);
            if (authenticate.isAuthenticated()) {
                return authenticatedHandler.onSuccessful(authenticate).getToken();
            }
            return null;
        } catch (AuthenticateException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateAuthenticationCode(AuthenticationRequest request) {
        String appid = request.getAppid();

        DefaultOAuth2TokenContext context = DefaultOAuth2TokenContext.builder()
                .tokenType(new OAuth2TokenType(request.getGrantType()))
                // 填充用户信息
                .principal(null)
                // 填充用户信息
                .registeredApp(null)
                .build();

        OAuth2Token auth2Token = this.tokenGenerator.generate(context);
        if (null == auth2Token) {
            return null;
        }
        OAuth2AuthorizationCode authorizationCode = (OAuth2AuthorizationCode) auth2Token;
        // 存储
        //返回
        return "";
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return null;
    }

    private void preprocess(AuthenticatedRequest request) {
        // todo 滑块校验、验证码验证、密码解密
    }
}
