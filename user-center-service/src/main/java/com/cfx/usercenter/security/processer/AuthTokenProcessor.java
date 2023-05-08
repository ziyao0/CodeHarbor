package com.cfx.usercenter.security.processer;

import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.core.LoginPostProcessor;
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
    public Authentication process(Authentication authentication) {
        return null;
    }
}
