package com.ziyao.harbor.usercenter.authenticate.provider;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import com.ziyao.harbor.usercenter.authenticate.query.UserQuery;
import com.ziyao.harbor.usercenter.authenticate.support.PasswordParameter;
import com.ziyao.harbor.usercenter.authenticate.support.UserStatusValidator;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.service.UserService;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * 用户密码主要身份验证器
 *
 * @author ziyao zhang
 * @since 2023/9/26
 */
@Service
public class OAuth2PasswordAuthenticator implements OAuth2Authenticator {

    private final UserService userService;

    public OAuth2PasswordAuthenticator(UserService userService) {
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
        UserStatusValidator.validated(userDetails);
        // 检查用户密码
        PasswordParameter parameter = PasswordParameter.of(
                authenticatedRequest.getPassword(), userDetails.getSecretKey());
//        PasswordValidator.validated(parameter);
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
        Assert.notNull(user);
        return user;
    }

    @Override
    public AuthorizationGrantType getAuthorizationGrantType() {
        return AuthorizationGrantType.PASSWORD;
    }
}
