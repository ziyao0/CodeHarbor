package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedRequest;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedUser;
import com.ziyao.harbor.usercenter.authenticate.core.AuthenticationType;
import com.ziyao.harbor.usercenter.comm.exception.AuthenticateExceptions;
import com.ziyao.harbor.web.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 认证提供管理器，通过实现{@link AuthenticatorManager} 认证管理器去处理认证核心逻辑
 * 认证实现是通过委托的设计模式去设计实现的，认证方案统一由{@link Authenticator}
 * 来提供.
 * 基于安全考虑，设计之初采用了双重认证（多因子认证）方案，即再{@link Authenticator#authenticate(AuthenticatedRequest)}
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

    private final Map<AuthenticationType, Authenticator> authenticatorsMapping;

    public DefaultAuthenticatorManager() {
        List<Authenticator> authenticators = ApplicationContextUtils.getBeansOfType(Authenticator.class);
        Assert.notNull(authenticators, "providers can not be empty.");
        this.authenticatorsMapping = this.initBeanMapping(authenticators);
    }

    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest request) {
        Authenticator authenticator = authenticatorsMapping.get(request.getAuthenticationType());
        if (authenticator != null) {
            if (!authenticator.supports(authenticator.getClass())) {
                log.error("当前认证处理不支持. {}", authenticator.getClass());
                throw AuthenticateExceptions.createValidatedFailure();
            }
            return authenticator.authenticate(request);
        } else
            throw AuthenticateExceptions.createValidatedFailure();
    }

}
