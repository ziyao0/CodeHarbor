package com.cfx.usercenter.security.processer;

import com.cfx.common.support.Tokens;
import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.cfx.usercenter.security.core.LoginPostProcessor;
import com.cfx.usercenter.security.support.SecurityUtils;
import org.springframework.stereotype.Component;

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

        String token = Tokens.create(
                details.getAppId(),
                details.getUserId(),
                details.getAccessKey(), SecurityUtils.OAUTH2_SECURITY);
        return new AccessToken(token);
    }
}
