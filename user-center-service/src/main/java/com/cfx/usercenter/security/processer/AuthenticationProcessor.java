package com.cfx.usercenter.security.processer;

import com.cfx.common.exception.ServiceException;
import com.cfx.common.writer.Errors;
import com.cfx.usercenter.dto.LoginDTO;
import com.cfx.usercenter.security.PrimaryAuthProviderManager;
import com.cfx.usercenter.security.api.AccessToken;
import com.cfx.usercenter.security.api.Authentication;
import com.cfx.usercenter.security.auth.AuthenticationDetails;
import com.cfx.usercenter.security.auth.FailureAuthDetails;
import com.cfx.usercenter.security.auth.SuccessAuthDetails;
import com.cfx.usercenter.security.core.GlobalProcessor;
import com.cfx.usercenter.security.handle.AuthenticationSuccessHandler;
import com.cfx.usercenter.security.support.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Slf4j
@Component
public class AuthenticationProcessor implements GlobalProcessor<LoginDTO, AccessToken> {

    @Autowired
    private PrimaryAuthProviderManager providerManager;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void preProcessBefore(LoginDTO loginDTO) {

        // TODO: 2023/5/8 可以做验证码等校验
    }

    @Override
    public AccessToken process(LoginDTO loginDTO) {
        try {
            // 前置处理
            this.preProcessBefore(loginDTO);

            Authentication authentication = attemptAuthentication(loginDTO);

            if (SecurityUtils.authenticated(authentication)) {
                SuccessAuthDetails successful = (SuccessAuthDetails) authentication;
                AccessToken accessToken = successfulAuthentication(successful);
                this.preProcessAfter(successful);
                return accessToken;
            } else
//                this.authenticationFailure((FailureAuthDetails) authentication);
                throw new ServiceException(((FailureAuthDetails) authentication).getMessage());
        } catch (ServiceException serviceException) {
            // 业务异常直接抛出
            throw serviceException;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Errors.INTERNAL_SERVER_ERROR);
        }
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

    private void authenticationFailure(FailureAuthDetails failureAuthDetails) {
        throw new ServiceException(failureAuthDetails.getMessage());
    }

    /**
     * 登录成功后处理
     *
     * @param successAuthDetails 登录成功后信息
     * @return 返回 token
     */
    private AccessToken successfulAuthentication(SuccessAuthDetails successAuthDetails) {
        log.info("欢迎[{}]成功登录系统!", successAuthDetails.getNickname());
        return authenticationSuccessHandler.onSuccess(successAuthDetails);
    }
}
