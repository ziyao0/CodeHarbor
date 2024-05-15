package com.ziyao.pay;

import com.ziyao.pay.core.PayType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ziyao
 * @since 2024/05/15 14:23:56
 */
@ConfigurationProperties("com.ziyao.pay")
public class PayProperties {

    private PayType payType;
    private String appId;
    private String appSecret;
    private String notifyUrl;
    private String returnUrl;
}
