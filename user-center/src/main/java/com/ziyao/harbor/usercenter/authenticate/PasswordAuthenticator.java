package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authenticate.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.authenticate.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedExceptions;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.mysql.QueryHandler;
import com.ziyao.harbor.usercenter.mysql.QueryProcessor;
import com.ziyao.harbor.usercenter.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户密码主要身份验证器
 *
 * @author ziyao zhang
 * @since 2023/9/26
 */
@Service
public class PasswordAuthenticator implements Authenticator {

    private final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();
    private final UserService userService;

    private final UserStatusValidator userStatusChecker = new UserStatusValidator();

    public PasswordAuthenticator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest) {

        Assert.notNull(authenticatedRequest, "认证信息不能为空！");
        return doAuthenticate(authenticatedRequest);
    }


    private AuthenticatedUser doAuthenticate(AuthenticatedRequest authenticatedRequest) {
        Long appId = authenticatedRequest.getAppid();
        appId = Strings.isEmpty(appId) ? 0 : appId;
        String username = authenticatedRequest.getUsername();
        Assert.notNull(username, "用户登陆名不能为空！");
        String password = authenticatedRequest.getPassword();
        // 获取用户信息
        AuthenticatedUser authenticatedUser = loadAuthenticatedUser(appId, password);
        User user = authenticatedUser.getUser();
        if (!passwordEncryptor.matches(password, user.getSecretKey())) {
            throw AuthenticatedExceptions.createValidatedFailure();
        }
        userStatusChecker.validate(user);
        return authenticatedUser;
    }

    @Override
    public AuthenticatedUser loadAuthenticatedUser(Long appid, String username) {
        User user = userService.loadUserDetails(appid, username);
        Assert.notNull(user, "为获取到用户信息！");
        return AuthenticatedUser.builder().user(user).build();
    }

    private QueryHandler prepare(String query) {
        return new QueryProcessor(null, query);
    }

}
