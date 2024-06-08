package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.core.jwt.JwtTokenGenerator;
import com.ziyao.harbor.core.token.JwtInfo;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.core.utils.SecurityUtils;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authenticate.core.UserDetails;
import com.ziyao.harbor.usercenter.common.exception.AuthenticateException;
import com.ziyao.harbor.usercenter.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/12/13
 */
@Service
public class DefaultAuthenticatedHandler implements AuthenticatedHandler {


    @Override
    public AuthenticatedUser onSuccessful(AuthenticatedUser authenticatedUser) {
        UserDetails user = authenticatedUser.getUser();


        JwtInfo build = JwtInfo.builder()
                .tokenType(TokenType.Bearer)
                .secret(SecurityUtils.loadJwtTokenSecret())
                .payload(createPayload((User) user)).build();
        JwtTokenGenerator tokenGenerator = new JwtTokenGenerator();
        String tokenValue = tokenGenerator.generateToken(build);
        authenticatedUser.setToken(tokenValue);
        return authenticatedUser;
    }

    private Map<String, Object> createPayload(User user) {

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("nickname", user.getNickname());
        payload.put("deptName", user.getDeptName());
        payload.put("status", user.getStatus());
        return payload;
    }

    @Override
    public void onFailure(AuthenticatedUser authenticatedUser, AuthenticateException exception) {

    }
}
