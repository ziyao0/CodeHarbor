package com.ziyao.harbor.usercenter.request;

import lombok.Data;

import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/07 12:36:00
 */
@Data
public class AuthenticationRequest {

    private String username;

    private String password;

    private Long appid;

    private String grantType;

    private String code;

    private String redirectUri;

    private String state;

    private Set<String> scopes;

    private String refreshToken;

}
