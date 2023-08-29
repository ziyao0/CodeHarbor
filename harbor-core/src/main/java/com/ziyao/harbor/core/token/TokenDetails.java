package com.ziyao.harbor.core.token;

import java.util.Date;
import java.util.Map;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public interface TokenDetails {

    /**
     * token类型
     */
    TokenType getTokenType();

    /**
     * 头部信息
     */
    Header getHeader();

    /**
     * 有效载荷
     */
    Map<String, Object> getPayload();

    /**
     * 过期时间
     */
    Date getExpiresAt();

    /**
     * 秘钥信息
     */
    String getSecret();

    /**
     * 头部信息
     */
    interface Header {
        String getIssuer();

        String getSubject();

        String[] getAudience();
    }
}
