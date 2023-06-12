package com.ziyao.cfx.usercenter.dto;

import com.ziyao.cfx.usercenter.security.api.ProviderName;
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

    private ProviderName loginType = ProviderName.PASSWD;
}
