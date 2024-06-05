package com.ziyao.harbor.usercenter.authenticate.core;

import com.ziyao.security.oauth2.core.AuthorizationGrantType;
import lombok.Data;

/**
 * @author ziyao
 * @since 2023/4/23
 */
@Data
public class AuthenticatedRequest {

    private Long appid;

    private String username;

    private String password;

    private boolean authenticated;

    private AuthorizationGrantType authorizationGrantType = AuthorizationGrantType.PASSWORD;
}
