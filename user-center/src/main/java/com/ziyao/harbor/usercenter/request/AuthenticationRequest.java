package com.ziyao.harbor.usercenter.request;

import lombok.Data;

/**
 * @author ziyao
 * @since 2024/06/07 12:36:00
 */
@Data
public class AuthenticationRequest {

    private Long appid;

    private String grantType;

    private String state;

}
