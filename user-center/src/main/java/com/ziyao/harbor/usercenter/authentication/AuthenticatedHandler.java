package com.ziyao.harbor.usercenter.authentication;

import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.common.exception.AuthenticateException;

/**
 * @author ziyao zhang
 * @since 2023/12/13
 */
public interface AuthenticatedHandler {

    AuthenticatedUser onSuccessful(AuthenticatedUser authenticatedUser);

    void onFailure(AuthenticatedUser authenticatedUser, AuthenticateException exception);

}
