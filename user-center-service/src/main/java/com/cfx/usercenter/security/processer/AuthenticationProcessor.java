package com.cfx.usercenter.security.processer;

import com.cfx.usercenter.dto.LoginDTO;
import com.cfx.usercenter.security.PrimaryAuthProviderManager;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.auth.AuthenticationDetails;
import com.cfx.usercenter.security.core.GlobalProcessor;
import com.cfx.usercenter.security.support.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Eason
 * @since 2023/5/8
 */
@Component
public class AuthenticationProcessor implements GlobalProcessor<LoginDTO> {

    @Resource
    private PrimaryAuthProviderManager providerManager;

    @Override
    public void preProcessBefore(LoginDTO loginDTO) {

        // TODO: 2023/5/8 可以做验证码等校验
    }

    @Override
    public Authentication process(LoginDTO loginDTO) {
        try {
            // 前置处理
            this.preProcessBefore(loginDTO);

            Authentication authentication = attemptAuthentication(loginDTO);

            if (SecurityUtils.authenticated(authentication)) {
                Authentication successful = successfulAuthentication(authentication);
                this.preProcessAfter(successful);
                return successful;
            } else
                this.authenticationFailure(authentication);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Authentication attemptAuthentication(LoginDTO loginDTO) {

        String accessKey = loginDTO.getAccessKey();
        accessKey = (accessKey != null) ? accessKey : "";
        String secretKey = loginDTO.getSecretKey();
        secretKey = (secretKey != null) ? secretKey : "";
        // 认证
        AuthenticationDetails authDetails = new AuthenticationDetails(loginDTO.getAppid(), accessKey, secretKey, loginDTO.getLoginType().name());
        return providerManager.authenticate(authDetails);
    }

    private void authenticationFailure(Authentication authentication) {

    }

    private Authentication successfulAuthentication(Authentication authentication) {

        return null;
    }
}
