package com.cfx.gateway.security.api;

import org.springframework.lang.NonNull;

/**
 * @author ziyao zhang
 * @since 2023/5/16
 */
public interface ProviderManager {

    /**
     * 授权处理
     *
     * @param authorization {@link Authorization}授权核心参数
     * @return 返回认证结果
     */
    Authorization authorize(@NonNull Authorization authorization);
}
