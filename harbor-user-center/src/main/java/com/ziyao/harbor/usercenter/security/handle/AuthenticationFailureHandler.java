package com.ziyao.harbor.usercenter.security.handle;

import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedException;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.core.FailureHandler;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public class AuthenticationFailureHandler implements FailureHandler<Authentication, AuthenticatedException> {


    @Override
    public void onFailure(Authentication authentication, AuthenticatedException e) {

    }
}
