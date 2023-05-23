package com.cfx.usercenter.security.processer;

import com.cfx.common.support.Tokens;
import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.cfx.usercenter.security.core.LoginPostProcessor;
import com.cfx.usercenter.security.support.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 处理并生成token
 *
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Component
public class AuthTokenProcessor implements LoginPostProcessor {

    @Override
    public AccessToken process(SuccessAuthDetails details) {

        Map<String, Object> payload = Tokens.TokenConverter.create().appid(details.getAppId())
                .email(details.getEmail()).phone(details.getPhone())
                .deptId(details.getDeptId())
                .deptName(details.getDeptName()).userId(details.getUserId())
                .username(details.getAccessKey()).nickname(details.getNickname()).build();
        String token = Tokens.create(payload, SecurityUtils.OAUTH2_SECURITY);
        return new AccessToken(token);
    }
}
