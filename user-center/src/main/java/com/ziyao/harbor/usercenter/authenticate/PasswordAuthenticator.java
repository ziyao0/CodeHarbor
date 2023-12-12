package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import com.ziyao.harbor.usercenter.authenticate.query.UserQuery;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticatedExceptions;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 用户密码主要身份验证器
 *
 * @author ziyao zhang
 * @since 2023/9/26
 */
@Service
public class PasswordAuthenticator implements Authenticator {

    private final UserService userService;

    private final UserStatusValidator userStatusValidator = new UserStatusValidator();

    public PasswordAuthenticator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest) {

        Assert.notNull(authenticatedRequest, "认证信息不能为空！");
        return doAuthenticate(authenticatedRequest);
    }


    private AuthenticatedUser doAuthenticate(AuthenticatedRequest authenticatedRequest) {
        // 组装查询条件
        UserQuery query = createdQuery(authenticatedRequest);
        // 获取用户信息
        UserDetails userDetails = loadUserDetails(query);
        // 校验用户状态
        userStatusValidator.validate(userDetails);
        // 检查用户密码
        PasswordValidator.validated(PasswordParameter.of(authenticatedRequest.getPassword(), userDetails.getSecretKey()));
        // 组装验证成功的用户信息
        return createdValidatorSuccessfulAuthenticatedUser(userDetails);
    }

    private @NonNull UserQuery createdQuery(AuthenticatedRequest authenticatedRequest) {
        Long appid = authenticatedRequest.getAppid();
        String username = authenticatedRequest.getUsername();
        Assert.notNull(username, "用户登陆名不能为空！");
        return UserQuery.of(Strings.isEmpty(appid) ? 0 : appid, username);
    }

    private AuthenticatedUser createdValidatorSuccessfulAuthenticatedUser(UserDetails userDetails) {
        return AuthenticatedUser.builder().user(userDetails).build();
    }

    @Override
    public UserDetails loadUserDetails(UserQuery query) {
        User user = userService.loadUserDetails(query.appid(), query.username());
        Assert.notNull(user, AuthenticatedExceptions.createValidatedFailure());
        return user;
    }

}
