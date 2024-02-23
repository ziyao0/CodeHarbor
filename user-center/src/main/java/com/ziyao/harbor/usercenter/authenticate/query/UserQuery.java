package com.ziyao.harbor.usercenter.authenticate.query;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/12/12
 */
@Getter
public class UserQuery {
    private final Long appid;
    private final String username;

    public UserQuery(Long appid, String username) {
        this.appid = appid;
        this.username = username;
    }

    public static UserQuery of(Long appid, String username) {
        return new UserQuery(appid, username);
    }
}
