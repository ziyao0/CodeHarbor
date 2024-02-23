package com.ziyao.harbor.usercenter.service.impl;

import com.ziyao.harbor.usercenter.authenticate.AuthenticatedHandler;
import com.ziyao.harbor.usercenter.authenticate.AuthenticatorManager;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticateException;
import com.ziyao.harbor.usercenter.service.AuthenticatedService;
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

    public AuthenticatedServiceImpl(AuthenticatorManager authenticatorManager,
                                    AuthenticatedHandler authenticatedHandler) {
        this.authenticatorManager = authenticatorManager;
        this.authenticatedHandler = authenticatedHandler;
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
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void preprocess(AuthenticatedRequest request) {
        // todo 滑块校验、验证码验证、密码解密
    }
}
