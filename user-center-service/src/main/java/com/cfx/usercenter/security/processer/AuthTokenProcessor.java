package com.cfx.usercenter.security.processer;

import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.api.UserInfo;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.cfx.usercenter.security.core.LoginPostProcessor;
import com.cfx.usercenter.security.support.Tokens;
import org.springframework.stereotype.Component;

/**
 * 处理并生成token
 *
 * @author Eason
 * @since 2023/5/8
 */
@Component
public class AuthTokenProcessor implements LoginPostProcessor {


    @Override
    public AccessToken process(SuccessAuthDetails details) {

        String token = Tokens.create(
                new UserInfo(details.getAppId(),
                        details.getUserId(),
                        details.getAccessKey()), "123");
        return new AccessToken(token);
    }
}
