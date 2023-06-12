package com.ziyao.cfx.usercenter.security.auth;

import com.ziyao.cfx.usercenter.security.api.Authentication;
import lombok.Data;

/**
 * @author ziyao zhang
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

    private String phone;

    private String email;

    private Long deptId;

    private String deptName;

    public SuccessAuthDetails(Long appId, Long userId, String accessKey, String nickname, String phone, String email, Long deptId, String deptName) {
        this.appId = appId;
        this.userId = userId;
        this.accessKey = accessKey;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.deptId = deptId;
        this.deptName = deptName;
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
