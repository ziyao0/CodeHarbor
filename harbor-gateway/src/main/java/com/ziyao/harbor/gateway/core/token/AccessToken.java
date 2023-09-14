package com.ziyao.harbor.gateway.core.token;

import com.ziyao.harbor.core.Named;

import java.io.Serializable;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface AccessToken extends Named, Serializable {

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

}
