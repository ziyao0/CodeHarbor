package com.cfx.usercenter.security.auth;

import com.cfx.usercenter.security.api.Authentication;
import lombok.Data;

/**
 * @author Eason
 * @since 2023/5/8
 */
@Data
public class SuccessAuthDetails implements Authentication {


    private Long appId;

    private Long userId;
    /**
     * 用户账号
     */
    private String accessKey;

    private String nickname;


    public SuccessAuthDetails(Long appId, Long userId, String accessKey, String nickname) {
        this.appId = appId;
        this.userId = userId;
        this.accessKey = accessKey;
        this.nickname = nickname;
    }

    @Override
    public String getSecretKey() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
