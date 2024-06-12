package com.ziyao.harbor.usercenter.service.impl;

import com.ziyao.harbor.usercenter.authentication.AuthenticatedHandler;
import com.ziyao.harbor.usercenter.authentication.AuthenticatorManager;
import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.DefaultOAuth2TokenContext;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.RegisteredApp;
import com.ziyao.harbor.usercenter.authentication.token.oauth2.generator.OAuth2TokenGenerator;
import com.ziyao.harbor.usercenter.common.exception.AuthenticateException;
import com.ziyao.harbor.usercenter.request.AuthenticationRequest;
import com.ziyao.harbor.usercenter.service.AuthenticationService;
import com.ziyao.harbor.usercenter.service.app.RegisteredAppService;
import com.ziyao.harbor.usercenter.service.oauth2.OAuth2AuthorizationService;
import com.ziyao.security.oauth2.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticatorManager authenticatorManager;
    private final AuthenticatedHandler authenticatedHandler;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private final RegisteredAppService registeredAppService;
    private final OAuth2AuthorizationService authorizationService;

    public AuthenticationServiceImpl(AuthenticatorManager authenticatorManager,
                                     AuthenticatedHandler authenticatedHandler,
                                     OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                     RegisteredAppService registeredAppService,
                                     OAuth2AuthorizationService authorizationService) {
        this.authenticatorManager = authenticatorManager;
        this.authenticatedHandler = authenticatedHandler;
        this.tokenGenerator = tokenGenerator;
        this.registeredAppService = registeredAppService;
        this.authorizationService = authorizationService;
    }

    @Override
    public String login(AuthenticatedRequest request) {
        try {
//            preprocess(request);
//            AuthenticatedUser authenticate = authenticatorManager.authenticate(request);
//            if (authenticate.isAuthenticated()) {
//                return authenticatedHandler.onSuccessful(authenticate).getToken();
//            }
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
    public OAuth2AuthorizationCode generateAuthenticationCode(AuthenticationRequest request) {

        RegisteredApp registeredApp = registeredAppService.findById(request.getAppid());

        if (registeredApp == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + request.getAppid() + "' was not found in the RegisteredClientRepository.");
        }

        DefaultOAuth2TokenContext context = DefaultOAuth2TokenContext.builder()
                .tokenType(new OAuth2TokenType(request.getGrantType()))
                .registeredApp(registeredApp)
                // 填充用户信息
                .build();

        OAuth2Token auth2Token = this.tokenGenerator.generate(context);
        if (null == auth2Token) {
            return null;
        }
        OAuth2AuthorizationCode authorizationCode = (OAuth2AuthorizationCode) auth2Token;

        OAuth2Authorization authorization = OAuth2Authorization.withRegisteredAppId(registeredApp.getAppId())
                .token(authorizationCode)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();

        authorizationService.save(authorization);

        return authorizationCode;
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return null;
    }

    private void preprocess(AuthenticatedRequest request) {
        // todo 滑块校验、验证码验证、密码解密
    }
}
