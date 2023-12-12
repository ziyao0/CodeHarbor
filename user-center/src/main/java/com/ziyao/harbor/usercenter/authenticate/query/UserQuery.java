package com.ziyao.harbor.usercenter.authenticate.query;

/**
 * @author ziyao zhang
 * @since 2023/12/12
 */
public record UserQuery(Long appid, String username) {

    public static UserQuery of(Long appid, String username) {
        return new UserQuery(appid, username);
    }
}
