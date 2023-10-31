package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.mysql.QueryHandler;
import com.ziyao.harbor.usercenter.mysql.QueryProcessor;
import com.ziyao.harbor.usercenter.security.UserStatusChecker;
import com.ziyao.harbor.usercenter.security.codec.BCryptPasswordEncryptor;
import com.ziyao.harbor.usercenter.security.codec.PasswordEncryptor;
import com.ziyao.harbor.usercenter.security.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.service.UserService;

/**
 * 用户密码主要身份验证器
 *
 * @author ziyao zhang
 * @since 2023/9/26
 */
public class PasswordAuthenticator implements Authenticator {

    private QueryHandler queryHandler;

    private final PasswordEncryptor passwordEncryptor = new BCryptPasswordEncryptor();
    private final UserService userService;

    private final UserStatusChecker userStatusChecker = new UserStatusChecker();

    public PasswordAuthenticator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public AuthenticatedUser authenticate(AuthenticateDetails authenticateDetails) {
        try {
            Assert.notNull(authenticateDetails, "认证信息不能为空！");
            return doAuthenticate(authenticateDetails);
        } catch (Exception e) {

        }
        return null;
    }


    private AuthenticatedUser doAuthenticate(AuthenticateDetails authenticateDetails) {
        try {
            Long appId = authenticateDetails.getAppId();
            appId = Strings.isEmpty(appId) ? 0 : appId;
            String accessKey = authenticateDetails.getAccessKey();
            Assert.notNull(accessKey, "用户登陆名不能为空！");
            String secretKey = authenticateDetails.getSecretKey();
            // 获取用户信息
            AuthenticatedUser authenticatedUser = loadAuthenticatedUser(appId, accessKey);
            User user = authenticatedUser.getUser();
            if (!passwordEncryptor.matches(secretKey, user.getSecretKey())) {
                // TODO: 2023/10/22 用户密码错误
            }
            userStatusChecker.validate(user);
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
