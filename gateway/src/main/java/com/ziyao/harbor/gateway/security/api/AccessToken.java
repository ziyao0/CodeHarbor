package com.ziyao.harbor.gateway.security.api;

import java.io.Serializable;
import java.util.Set;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface AccessToken extends Serializable {

    /**
     * 访问令牌
     */
    String getToken();

    /**
     * 刷新令牌
     */
    String getRefreshToken();

    /**
     * 获取时间戳
     */
    String getTimestamp();

    /**
     * 授权范围
     */
    Set<String> getScope();
}
