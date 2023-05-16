package com.cfx.gateway.security.core;

import com.cfx.gateway.security.api.Authorization;
import com.cfx.gateway.security.api.AuthorizationProcessor;
import com.cfx.gateway.security.api.ProviderManager;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * 授权处理器
 * <p>
 * 说明：授权处理器是新平台认证中心2.0提供的全量授权处理API，通过实现{@link AuthorizationProcessor}全局处理器
 * 在授权之前、授权、授权之后分别去做不同的授权处理，很大程度的对授权业务进行了代码拆封，更加稳定、简洁。
 * <p>
 * 授权处理核心 {@link com.cfx.gateway.security.api.ProviderManager}授权管理器
 * 授权管理器提供了{@link ProviderManager#authorize(Authorization)}完整安全的授权处理方案，授权管理器通过
 * 委托的设计模式把授权操作委托给{@link com.cfx.gateway.security.api.Provider}
 * 授权提供者，授权管理器通过管理授权提供者去进行token验证和越权访问等相关的授权工作。
 * <p>
 * 授权处理器{@link DefaultAuthorizationProcessor#processAfter(Authorization)}对认证成功的token进行过期时间刷新
 *
 * @author Eason
 * @since 2023/5/16
 */
public class DefaultAuthorizationProcessor implements AuthorizationProcessor {


    private ProviderManager providerManager;


    public DefaultAuthorizationProcessor(ProviderManager providerManager) {
        this.providerManager = providerManager;
    }

    @Override
    public Mono<Authorization> process(@NonNull Mono<Authorization> authorizationMono) {

        return authorizationMono.flatMap(requestAuthor -> {
            if (requestAuthor.isAuthorized())
                return Mono.just(new SuccessAuthorization());
            else
                return Mono.just(getProviderManager().authorize(requestAuthor));

        });
    }

    public ProviderManager getProviderManager() {
        return providerManager;
    }

    public void setProviderManager(ProviderManager providerManager) {
        this.providerManager = providerManager;
    }
}
