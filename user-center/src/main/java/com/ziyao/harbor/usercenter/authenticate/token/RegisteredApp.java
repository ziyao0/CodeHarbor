package com.ziyao.harbor.usercenter.authenticate.token;

import com.ziyao.security.oauth2.settings.TokenSettings;
import lombok.Data;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Data
public class RegisteredApp {

    private int appid;

    private String appName;

    private String redirectUri;

    private TokenSettings tokenSettings;
}
