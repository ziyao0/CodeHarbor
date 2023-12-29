package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.error.Exceptions;
import com.ziyao.harbor.gateway.core.token.Authorization;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 授权管理器，授权核心逻辑。实现{@link AuthorizerManager}授权管理处理授权核心
 * 逻辑，授权是通过委托的设计模式实现多条件授权过滤的，授权方案统一由
 * {@link Authorizer}提供.
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
public class DefaultAuthorizerManager implements AuthorizerManager {

    private final Map<String, Authorizer> authorizerMap;


    public DefaultAuthorizerManager(List<Authorizer> authorizers) {
        // 初始化所有鉴权提供者
        this.authorizerMap = authorizers.stream().collect(
                Collectors.toMap(Authorizer::getName, Function.identity()));
    }

    @Override
    public Authorizer getAuthorizer(String name) {
        Authorizer authorizer = getAuthorizerMap().get(name);
        if (Objects.isNull(authorizer)) {
            throw Exceptions.createIllegalAccessException("未获取到对应的授权方，name：" + name);
        }
        return authorizer;
    }
}
