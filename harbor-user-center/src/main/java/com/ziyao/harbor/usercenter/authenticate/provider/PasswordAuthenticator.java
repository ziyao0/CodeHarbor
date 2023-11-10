package com.ziyao.harbor.usercenter.authenticate.provider;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authenticate.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.Authenticator;
import com.ziyao.harbor.usercenter.authenticate.UserStatusValidator;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedExceptions;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.mysql.QueryHandler;
import com.ziyao.harbor.usercenter.mysql.QueryProcessor;
import com.ziyao.harbor.usercenter.authenticate.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.authenticate.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.security.core.AuthenticatedUser;
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

    private static final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();
    private final UserService userService;

    private static final UserStatusValidator validator = new UserStatusValidator();

    public PasswordAuthenticator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest) {
        try {
            Assert.notNull(authenticatedRequest, "认证信息不能为空！");
            return doAuthenticate(authenticatedRequest);
        } catch (Exception e) {
            throw AuthenticatedExceptions.createValidatedFailure();
        }
    }


    private AuthenticatedUser doAuthenticate(AuthenticatedRequest authenticatedRequest) {
        try {
            Long appId = authenticatedRequest.getAppid();
            appId = Strings.isEmpty(appId) ? 0 : appId;
            String accessKey = authenticatedRequest.getAccessKey();
            Assert.notNull(accessKey, "用户登陆名不能为空！");
            String secretKey = authenticatedRequest.getSecretKey();
            // 获取用户信息
            AuthenticatedUser authenticatedUser = loadAuthenticatedUser(appId, accessKey);
            User user = authenticatedUser.getUser();
            if (!passwordEncryptor.matches(secretKey, user.getSecretKey())) {
                // TODO: 2023/10/22 用户密码错误
            }
            validator.validate(user);
            return authenticatedUser;
        } catch (Exception e) {

        }
        return null;
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
