package com.ziyao.cfx.usercenter.security.handle;

import com.ziyao.cfx.usercenter.comm.exception.AuthenticationException;
import com.ziyao.cfx.usercenter.security.api.Authentication;
import com.ziyao.cfx.usercenter.security.core.FailureHandler;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public class AuthenticationFailureHandler implements FailureHandler<Authentication, AuthenticationException> {


    @Override
    public void onFailure(Authentication authentication, AuthenticationException e) {

    }
}
