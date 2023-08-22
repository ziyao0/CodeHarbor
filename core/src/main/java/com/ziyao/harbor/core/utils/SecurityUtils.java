package com.ziyao.harbor.core.utils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;

/**
 * @author ziyao zhang
 * @since 2023/6/26
 */
public abstract class SecurityUtils {
    private static final String OAUTH2_SECURITY = "ziyao:oauth2:security";

    /**
     * Jwt Token 用于签名的 Key(勿更改).
     */
    private static final Key SIGN_KEY;


    static {
        SIGN_KEY = new SecretKeySpec(OAUTH2_SECURITY.getBytes(Charset.defaultCharset()), "HmacSHA256");
    }

    private SecurityUtils() throws InstantiationException {
        throw new InstantiationException();
    }


    /**
     * 加载用于 Jwt 令牌签名的字符串格式密钥.
     *
     * @return Jwt 令牌签名说下密钥
     */
    public static String loadJwtTokenSecret() {
        return OAUTH2_SECURITY;
    }
}
