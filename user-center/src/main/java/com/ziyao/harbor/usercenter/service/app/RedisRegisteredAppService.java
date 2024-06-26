package com.ziyao.harbor.usercenter.service.app;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.repository.redis.RegisteredAppRepositoryRedis;
import com.ziyao.security.oauth2.core.RegisteredApp;

/**
 * @author ziyao
 * @since 2024/06/08 17:28:26
 */
public class RedisRegisteredAppService implements RegisteredAppService {

    private final RegisteredAppRepositoryRedis registeredAppRepository;

    public RedisRegisteredAppService(RegisteredAppRepositoryRedis registeredAppRepository) {
        this.registeredAppRepository = registeredAppRepository;
    }

    @Override
    public void save(RegisteredApp registeredApp) {
        Assert.notNull(registeredApp, "registeredApp must not be null");
        registeredAppRepository.save(registeredApp);
    }

    @Override
    public RegisteredApp findById(Long appId) {
        Assert.notNull(appId, "appId must not be null");
        return registeredAppRepository.findById(appId).orElse(null);
    }


    @Override
    public Model model() {
        return Model.redis;
    }
}
