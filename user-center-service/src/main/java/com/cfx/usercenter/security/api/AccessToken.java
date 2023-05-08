package com.cfx.usercenter.security.api;

import lombok.Data;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Data
public class AccessToken {


    private String token;

    public AccessToken(String token) {
        this.token = token;
    }
}
