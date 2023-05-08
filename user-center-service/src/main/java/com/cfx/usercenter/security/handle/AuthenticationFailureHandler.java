package com.cfx.usercenter.security.handle;

import com.cfx.usercenter.comm.exception.AuthenticationException;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.core.FailureHandler;

/**
 * @author Eason
 * @since 2023/5/8
 */
public class AuthenticationFailureHandler implements FailureHandler<Authentication, AuthenticationException> {


    @Override
    public void onFailure(Authentication authentication, AuthenticationException e) {

    }
}
