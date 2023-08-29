package com.ziyao.harbor.usercenter.security.handle;

import com.ziyao.harbor.usercenter.comm.exception.AuthenticationException;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.core.FailureHandler;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public class AuthenticationFailureHandler implements FailureHandler<Authentication, AuthenticationException> {


    @Override
    public void onFailure(Authentication authentication, AuthenticationException e) {

    }
}
