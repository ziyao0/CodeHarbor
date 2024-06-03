package com.ziyao.harbor.usercenter.oauth2;

import com.ziyao.oauth2.core.OAuth2AccessToken;
import com.ziyao.oauth2.core.OAuth2RefreshToken;

/**
 * @author ziyao
 * @since 2024/06/03 09:54:21
 */
public interface OAuth2TokenContext {


    OAuth2AccessToken getAccessToken();

    OAuth2RefreshToken getRefreshToken();

}
