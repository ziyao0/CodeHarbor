package com.ziyao.harbor.usercenter.dto;

import com.ziyao.harbor.usercenter.authenticate.core.AuthenticatedType;
import lombok.Data;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
@Data
public class LoginDTO {

    private Long appid;

    private String secretKey;

    private String accessKey;

    private AuthenticatedType loginType = AuthenticatedType.PASSWD;
}