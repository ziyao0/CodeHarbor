package com.ziyao.harbor.usercenter.service.app;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authenticate.token.RegisteredApp;
import com.ziyao.harbor.usercenter.repository.redis.RedisRegisteredAppRepository;

/**
 * @author ziyao
 * @since 2024/06/08 17:28:26
 */
public class RedisRegisteredAppService implements RegisteredAppService {

    private final RedisRegisteredAppRepository registeredAppRepository;

    public RedisRegisteredAppService(RedisRegisteredAppRepository registeredAppRepository) {
        this.registeredAppRepository = registeredAppRepository;
    }

    @Override
    public void save(RegisteredApp registeredApp) {
        Assert.notNull(registeredApp, "registeredApp must not be null");
        registeredAppRepository.opsForHash().put(registeredApp.getAppId(), registeredApp);
    }

    @Override
    public RegisteredApp findById(Long appId) {
        Assert.notNull(appId, "appId must not be null");
        return registeredAppRepository.opsForHash().get(appId);
    }


    @Override
    public Model model() {
        return Model.redis;
    }
}
