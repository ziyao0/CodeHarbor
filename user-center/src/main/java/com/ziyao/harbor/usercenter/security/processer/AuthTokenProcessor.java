package com.ziyao.harbor.usercenter.security.processer;

import com.ziyao.harbor.common.token.Tokens;
import com.ziyao.harbor.common.utils.SecurityUtils;
import com.ziyao.harbor.usercenter.security.api.AccessToken;
import com.ziyao.harbor.usercenter.security.auth.SuccessAuthDetails;
import com.ziyao.harbor.usercenter.security.core.LoginPostProcessor;
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
        String token = Tokens.create(payload, SecurityUtils.loadJwtTokenSecret());
        return new AccessToken(token);
    }
}
