package com.ziyao.cfx.usercenter.security.api;

import lombok.Data;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Data
public class UserInfo {

    private Long appId;

    private Long userId;

    private String username;

    public UserInfo() {
    }

    public UserInfo(Long appId, Long userId, String username) {
        this.appId = appId;
        this.userId = userId;
        this.username = username;
    }
}
