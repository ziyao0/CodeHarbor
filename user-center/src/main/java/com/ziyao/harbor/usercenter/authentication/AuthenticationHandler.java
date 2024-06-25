package com.ziyao.harbor.usercenter.authentication;

import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.common.exception.AuthenticationFailureException;

/**
 * @author ziyao zhang
 * @since 2023/12/13
 */
public interface AuthenticationHandler {

    AuthenticatedUser onSuccessful(AuthenticatedUser authenticatedUser);

    void onFailure(AuthenticatedUser authenticatedUser, AuthenticationFailureException exception);

}
