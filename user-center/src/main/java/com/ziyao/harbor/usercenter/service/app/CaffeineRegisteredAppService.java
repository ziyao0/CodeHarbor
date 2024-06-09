package com.ziyao.harbor.usercenter.service.app;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authenticate.token.RegisteredApp;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyao
 * @since 2024/06/08 17:27:40
 */
public class CaffeineRegisteredAppService implements RegisteredAppService {

    private final Cache<Long, RegisteredApp> registeredApps;

    public CaffeineRegisteredAppService() {
        registeredApps = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(7, TimeUnit.DAYS)
                .build();
    }


    @Override
    public void save(RegisteredApp registeredApp) {
        Assert.notNull(registeredApp, "registeredApp must not be null");
        registeredApps.put(registeredApp.getAppId(), registeredApp);
    }

    @Override
    public RegisteredApp findById(Long appId) {
        return registeredApps.getIfPresent(appId);
    }

    @Override
    public Model model() {
        return Model.caffeine;
    }
}
