package com.cfx.usercenter.security.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
@Data
public class AccessToken implements Serializable {


    private static final long serialVersionUID = 183398883755769022L;

    private String token;

    private String ip;

    public AccessToken(String token) {
        this.token = token;
    }
}
