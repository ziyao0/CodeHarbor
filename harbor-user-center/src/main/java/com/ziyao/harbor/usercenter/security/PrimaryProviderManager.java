package com.ziyao.harbor.usercenter.security;

import com.ziyao.harbor.usercenter.comm.exception.Errors;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.auth.FailureAuthDetails;
import com.ziyao.harbor.usercenter.security.core.AuthenticationProvider;
import com.ziyao.harbor.usercenter.security.core.PrimaryProvider;
import com.ziyao.harbor.usercenter.security.core.ProviderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 认证提供管理器，通过实现{@link ProviderManager} 认证管理器去处理认证核心逻辑
 * 认证实现是通过委托的设计模式去设计实现的，认证方案统一由{@link AuthenticationProvider}
 * 来提供.
 * 基于安全考虑，设计之初采用了双重认证（多因子认证）方案，即再{@link AuthenticationProvider#authenticate(Authentication)}
 * 认证通过的集成上提供了多因子认证方案，即通过{@link PrimaryProviderManager} 来提供多因子
 * 认证方案,执行{@linkplain ProviderManager#authenticate(Authentication)}该认证的前提是，首次认证的结果
 * {@link Authentication#isAuthenticated()}必须为true，false或者null则代表在第一重验证的时候没有通过.
 * </p>
 *
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Slf4j
public class PrimaryProviderManager implements ProviderManager {


    private final Map<String, PrimaryProvider> providersMapping;

    public PrimaryProviderManager(List<PrimaryProvider> providers) {
        Assert.notNull(providers, "providers can not be empty.");
        this.providersMapping = this.initBeanMapping(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String providerName = authentication.getProviderName();
        PrimaryProvider primaryAuthProvider = providersMapping.get(providerName);
        if (Objects.nonNull(primaryAuthProvider)) {
            if (!primaryAuthProvider.supports(authentication.getClass())) {
                log.error("当前认证处理不支持. {}", authentication.getClass());
                return new FailureAuthDetails(Errors.ERROR_100008);
            }
            return primaryAuthProvider.authenticate(authentication);
        }
        return new FailureAuthDetails(Errors.ERROR_100008);
    }
}
