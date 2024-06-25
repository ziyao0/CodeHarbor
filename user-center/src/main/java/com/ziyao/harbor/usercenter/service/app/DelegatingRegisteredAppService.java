package com.ziyao.harbor.usercenter.service.app;

import com.ziyao.security.oauth2.core.RegisteredApp;

import java.util.List;

/**
 * @author ziyao
 * @since 2024/06/08 17:27:09
 */
public class DelegatingRegisteredAppService implements RegisteredAppService {

    private RegisteredAppService redisRegisteredAppService;
    private RegisteredAppService memoryRegisteredAppService;
    private RegisteredAppService jpaRegisteredAppService;

    public DelegatingRegisteredAppService(List<RegisteredAppService> registeredAppServices) {
        for (RegisteredAppService registeredAppService : registeredAppServices) {
            switch (registeredAppService.model()) {
                case caffeine -> this.memoryRegisteredAppService = registeredAppService;
                case redis -> this.redisRegisteredAppService = registeredAppService;
                case jpa -> this.jpaRegisteredAppService = registeredAppService;
                default -> throw new IllegalStateException("Unexpected value: " + registeredAppService.model());
            }
        }
    }


    @Override
    public void save(RegisteredApp registeredApp) {
        this.jpaRegisteredAppService.save(registeredApp);
        registeredApp = this.jpaRegisteredAppService.findById(registeredApp.getAppId());

        this.redisRegisteredAppService.save(registeredApp);
        this.memoryRegisteredAppService.save(registeredApp);
    }

    @Override
    public RegisteredApp findById(Long appId) {
        RegisteredApp registeredApp = this.memoryRegisteredAppService.findById(appId);

        if (registeredApp == null) {
            registeredApp = this.redisRegisteredAppService.findById(appId);
            if (registeredApp == null) {
                registeredApp = this.jpaRegisteredAppService.findById(appId);
                if (registeredApp != null) {
                    this.redisRegisteredAppService.save(registeredApp);
                    this.memoryRegisteredAppService.save(registeredApp);
                }
            } else {
                this.memoryRegisteredAppService.save(registeredApp);
            }
        }

        return registeredApp;
    }
}
