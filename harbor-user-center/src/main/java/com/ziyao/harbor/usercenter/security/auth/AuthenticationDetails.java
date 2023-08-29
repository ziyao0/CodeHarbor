package com.ziyao.harbor.usercenter.security.auth;

import com.ziyao.harbor.usercenter.security.api.Authentication;
import lombok.Data;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Data
public class AuthenticationDetails implements Authentication {


    private Long appId;

    /**
     * 用户账号
     */
    private String accessKey;

    /**
     * 用户凭证
     */
    private String secretKey;

    /**
     * 登录类型
     */
    private String providerName;


    public AuthenticationDetails(Long appId, String accessKey, String secretKey, String providerName) {
        this.appId = appId;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.providerName = providerName;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }
}
