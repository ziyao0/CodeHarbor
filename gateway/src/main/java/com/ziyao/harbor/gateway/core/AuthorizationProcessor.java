package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.gateway.core.token.Authorization;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Mono;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface AuthorizationProcessor extends Ordered {


    /**
     * 前置处理器，在 {@link AuthorizationProcessor#process(Authorization)} 执行前执行
     *
     * @param authorization 基础信息详情
     * @return 返回 {@link Authorization}
     */
    @Nullable
    default Mono<Authorization> processBefore(@NonNull Mono<Authorization> authorization) {
        return authorization;
    }

    /**
     * 全局处理器
     *
     * @param authorizationMono {@link Authorization} 用户授权请求的数据
     * @return 返回处理对象
     */
    Authorization process(@NonNull Authorization authorizationMono);

    /**
     * 后置处理器 在 {@link AuthorizationProcessor#process(Authorization)} 后执行
     *
     * @param authorization 授权信息
     */
    @Nullable
    default void processAfter(@Nullable Authorization authorization) {

    }

    default int getOrder() {
        return 0;
    }
}
