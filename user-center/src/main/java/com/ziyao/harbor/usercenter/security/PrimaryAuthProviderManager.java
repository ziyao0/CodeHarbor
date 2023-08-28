package com.ziyao.harbor.usercenter.security;

import com.ziyao.harbor.usercenter.comm.exception.ErrorsIMessage;
import com.ziyao.harbor.usercenter.security.api.Authentication;
import com.ziyao.harbor.usercenter.security.auth.FailureAuthDetails;
import com.ziyao.harbor.usercenter.security.core.AuthenticationProvider;
import com.ziyao.harbor.usercenter.security.core.PrimaryAuthProvider;
import com.ziyao.harbor.usercenter.security.core.ProviderManager;
import com.ziyao.harbor.web.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 认证提供管理器，通过实现{@link ProviderManager} 认证管理器去处理认证核心逻辑
 * 认证实现是通过委托的设计模式去设计实现的，认证方案统一由{@link AuthenticationProvider}
 * 来提供.
 * 基于安全考虑，设计之初采用了双重认证（多因子认证）方案，即再{@link AuthenticationProvider#authenticate(Authentication)}
 * 认证通过的集成上提供了多因子认证方案，即通过{@link PrimaryAuthProviderManager#getChildProviderManager()} 来提供多因子
 * 认证方案,执行{@linkplain ProviderManager#authenticate(Authentication)}该认证的前提是，首次认证的结果
 * {@link Authentication#isAuthenticated()}必须为true，false或者null则代表在第一重验证的时候没有通过.
 * </p>
 *
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Slf4j
public class PrimaryAuthProviderManager implements ProviderManager {


    private final List<PrimaryAuthProvider> providers;
    private final Map<String, String> beanNameMapping;
    private final ProviderManager childProviderManager;

    public PrimaryAuthProviderManager(List<PrimaryAuthProvider> providers, ProviderManager providerManager) {
        Assert.notNull(providers, "providers can not be empty.");
        this.providers = providers;
        this.childProviderManager = providerManager;
        this.beanNameMapping = this.initBeanMapping(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        String providerName = authentication.getProviderName();

        String beanName = beanNameMapping.get(providerName);

        PrimaryAuthProvider provider = ApplicationContextUtils.getBean(beanName, PrimaryAuthProvider.class);

        if (!provider.supports(authentication.getClass())) {
            log.error("当前认证处理不支持. {}", authentication.getClass());
            return new FailureAuthDetails(ErrorsIMessage.UN_SUPPORTS);
        }
        return provider.authenticate(authentication);
    }


    public List<PrimaryAuthProvider> getProviders() {
        return providers;
    }

    public Map<String, String> getBeanNameMapping() {
        return beanNameMapping;
    }

    public ProviderManager getChildProviderManager() {
        return childProviderManager;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
