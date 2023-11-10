package com.ziyao.harbor.usercenter.authenticate;

import com.ziyao.harbor.usercenter.security.core.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ziyao zhang
 * @since 2023/11/10
 */
@Slf4j
@Service
public class DefaultAuthenticatorManager implements AuthenticatorManager {

    private final Map<String, Authenticator> authenticatorsMapping;

    public DefaultAuthenticatorManager(List<Authenticator> authenticators) {
        Assert.notNull(authenticators, "providers can not be empty.");
        this.authenticatorsMapping = this.initBeanMapping(authenticators);
    }

    @Override
    public AuthenticatedUser authenticate(AuthenticatedRequest authenticatedRequest) {
        Authenticator authenticator = authenticatorsMapping.get("providerName");
        if (Objects.nonNull(authenticator)) {
            if (!authenticator.supports(authenticator.getClass())) {
                log.error("当前认证处理不支持. {}", authenticator.getClass());
                return null;
            }
            return authenticator.authenticate(authenticatedRequest);
        }
        return null;
    }

}
