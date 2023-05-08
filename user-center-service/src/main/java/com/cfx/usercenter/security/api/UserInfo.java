package com.cfx.usercenter.security.api;

import com.cfx.usercenter.security.support.Tokens;
import lombok.Data;

/**
 * @author Eason
 * @since 2023/5/8
 */
@Data
public class UserInfo {

    private Long appId;

    private Long userId;

    private String username;


    public static void main(String[] args) {

        UserInfo userInfo = new UserInfo();

        userInfo.setAppId(841994265727877120L);
        userInfo.setUserId(841994265727877120L);
        userInfo.setUsername("zhangziyao");


        String s = Tokens.create(userInfo, "123");

        System.out.println(s);

    }
}
