package com.ziyao.harbor.usercenter.authenticate;

import lombok.Data;

/**
 * 认证请求
 *
 * @author ziyao
 * @since 2023/4/23
 */
@Data
public class AuthenticatedRequest {

    private Long appid;

    private String accessKey;

    private String secretKey;

    private boolean authenticated;
}
