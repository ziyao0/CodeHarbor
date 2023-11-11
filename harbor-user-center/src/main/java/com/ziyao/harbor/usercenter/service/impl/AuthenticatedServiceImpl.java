package com.ziyao.harbor.usercenter.service.impl;

import com.ziyao.harbor.usercenter.authenticate.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.AuthenticatorManager;
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

    private AuthenticatorManager authenticatorManager;

    @Override
    public Object login(AuthenticatedRequest authenticatedRequest) {
        return null;
    }
}
