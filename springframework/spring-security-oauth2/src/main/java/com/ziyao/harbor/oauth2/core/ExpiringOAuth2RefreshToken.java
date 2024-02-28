package com.ziyao.harbor.oauth2.core;

import java.util.Date;

/**
 * @author ziyao zhang
 * @since 2024/2/27
 */
public interface ExpiringOAuth2RefreshToken extends OAuth2RefreshToken {
    Date getExpiration();
}
