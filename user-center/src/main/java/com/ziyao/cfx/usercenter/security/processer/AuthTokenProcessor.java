package com.ziyao.cfx.usercenter.security.processer;

import com.ziyao.cfx.common.jwt.Tokens;
import com.ziyao.cfx.common.utils.SecurityUtils;
import com.ziyao.cfx.usercenter.security.api.AccessToken;
import com.ziyao.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.ziyao.cfx.usercenter.security.core.LoginPostProcessor;
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
