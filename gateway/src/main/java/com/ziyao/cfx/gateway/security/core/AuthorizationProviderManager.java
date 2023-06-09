package com.ziyao.cfx.gateway.security.core;

import com.ziyao.cfx.gateway.security.api.Authorization;
import com.ziyao.cfx.gateway.security.api.Provider;
import com.ziyao.cfx.gateway.security.api.ProviderManager;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 授权管理器，授权核心逻辑。实现{@link ProviderManager}授权管理处理授权核心
 * 逻辑，授权是通过委托的设计模式实现多条件授权过滤的，授权方案统一由
 * {@link Provider}提供.
 * <p>
 * {@link Authorization}说明：
 * 授权统一入口，由各个授权方案去处理并返回{@link Authorization}授权结果，采用所有
 * 条件都满足之后，即最终返回的{@link Authorization}不为空并且{@link Authorization#isAuthorized()}
 * 为true则表示授权成功.
 * </p>
 *
 * @author ziyao zhang
 * @since 2023/5/16
 */
public class AuthorizationProviderManager implements ProviderManager {

    private List<Provider> providers;


    public AuthorizationProviderManager(List<Provider> providers) {
        this.providers = providers;
    }


    @Override
    public Authorization authorize(@NonNull Authorization authorization) {
        Authorization authorize = new FailureAuthorization();
        for (Provider provider : getProviders()) {
            authorize = provider.authorize(authorization);
        }
        return authorize;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
