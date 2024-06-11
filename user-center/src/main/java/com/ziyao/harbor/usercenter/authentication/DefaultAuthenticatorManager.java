package com.ziyao.harbor.usercenter.authentication;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authentication.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authentication.provider.OAuth2Authenticator;
import com.ziyao.harbor.usercenter.common.exception.AuthenticateExceptions;
import com.ziyao.harbor.web.ApplicationContextUtils;
import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 认证提供管理器，通过实现{@link AuthenticatorManager} 认证管理器去处理认证核心逻辑
 * 认证实现是通过委托的设计模式去设计实现的，认证方案统一由{@link OAuth2Authenticator}
 * 来提供.
 * 基于安全考虑，设计之初采用了双重认证（多因子认证）方案，即再{@link OAuth2Authenticator#authenticate(AuthenticatedRequest)}
 * 认证通过的集成上提供了多因子认证方案，即通过{@link AuthenticatorManager} 来提供多因子
 * 认证方案,执行{@linkplain AuthenticatorManager#authenticate(AuthenticatedRequest)}该认证的前提是，首次认证的结果
 * {@link AuthenticatedUser#isAuthenticated()}必须为true，false或者null则代表在第一重验证的时候没有通过.
 * </p>
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Slf4j
@Service
public class DefaultAuthenticatorManager implements AuthenticatorManager {

    private final Map<AuthorizationGrantType, OAuth2Authenticator> authenticatorsMapping;

    public DefaultAuthenticatorManager() {
        List<OAuth2Authenticator> OAuth2Authenticators = ApplicationContextUtils.getBeansOfType(OAuth2Authenticator.class);
        Assert.notNull(OAuth2Authenticators, "providers can not be empty.");
        this.authenticatorsMapping = this.initBeanMapping(OAuth2Authenticators);
    }

    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest request) {
        OAuth2Authenticator OAuth2Authenticator = authenticatorsMapping.get(request.getAuthorizationGrantType());
        if (OAuth2Authenticator != null) {
            if (!OAuth2Authenticator.supports(OAuth2Authenticator.getClass())) {
                log.error("当前认证处理不支持. {}", OAuth2Authenticator.getClass());
                throw AuthenticateExceptions.createValidatedFailure();
            }
            return OAuth2Authenticator.authenticate(request);
        } else
            throw AuthenticateExceptions.createValidatedFailure();
    }

}
