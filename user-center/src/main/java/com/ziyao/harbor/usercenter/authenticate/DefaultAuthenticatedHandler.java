package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedException;
import org.springframework.stereotype.Service;

/**
 * @author ziyao zhang
 * @since 2023/12/13
 */
@Service
public class DefaultAuthenticatedHandler implements AuthenticatedHandler {


    @Override
    public AuthenticatedUser onSuccessful(AuthenticatedUser authenticatedUser) {
        return null;
    }

    @Override
    public void onFailure(AuthenticatedUser authenticatedUser, AuthenticatedException exception) {

    }
}
