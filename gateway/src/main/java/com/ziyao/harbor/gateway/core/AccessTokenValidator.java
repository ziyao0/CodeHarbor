package com.ziyao.harbor.gateway.core;

import com.ziyao.harbor.core.Validator;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.gateway.core.token.AccessToken;

/**
 * @author ziyao zhang
 * @since 2023/8/24
 */
public abstract class AccessTokenValidator implements Validator<AccessToken> {

    /**
     * 时间戳默认有效时长为 60秒.
     */
    private static final long TIMESTAMP_EXPIRATION_TIME = 60 * 1000L;

    private static final AccessTokenValidator ACCESS_TOKEN_VALIDATOR;

    static {
        ACCESS_TOKEN_VALIDATOR = new AccessTokenValidator() {
            @Override
            public void validate(AccessToken accessToken) {
                super.validate(accessToken);
            }
        };
    }


    @Override
    public void validate(AccessToken accessToken) {
        // 校验认证参数
        doValidate(accessToken);
    }

    private void doValidate(AccessToken accessToken) {
        Assert.notNull(accessToken, "缺少安全验证信息");
        Assert.notNull(accessToken.getToken(), "缺少认证头(Authorization)");
        Assert.notNull(accessToken.getTimestamp(), "缺失时间戳(timestamp)");
        Assert.isTimestampNotExpire(accessToken.getTimestamp(), getTimestampValidity());
    }

    private static long getTimestampValidity() {
        return TIMESTAMP_EXPIRATION_TIME;
    }

    /**
     * 快速校验访问令牌
     *
     * @param accessToken 访问令牌
     */
    public static void validateToken(AccessToken accessToken) {
        ACCESS_TOKEN_VALIDATOR.validate(accessToken);
    }
}
