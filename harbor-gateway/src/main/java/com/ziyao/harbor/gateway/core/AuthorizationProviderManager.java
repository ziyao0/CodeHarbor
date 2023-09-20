package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.gateway.core.token.AccessToken;
import com.ziyao.harbor.gateway.core.token.Authorization;
import lombok.Getter;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
@Getter
public class AuthorizationProviderManager implements ProviderManager {

    private final Map<String, Provider> providers;


    public AuthorizationProviderManager(List<Provider> providers) {
        // 初始化所有鉴权提供者
        this.providers = providers.stream().collect(
                Collectors.toMap(Provider::getName, Function.identity()));
    }


    @Override
    public Mono<Authorization> authorize(@NonNull AccessToken accessToken) {
        Provider provider = getProviders().get(accessToken.getName());
        return provider.authorize(accessToken);
    }
}
