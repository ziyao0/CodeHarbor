package com.ziyao.harbor.gateway.security.core;

import com.ziyao.harbor.core.Assert;
import com.ziyao.harbor.core.Validator;
import com.ziyao.harbor.gateway.security.api.AccessToken;

/**
 * @author ziyao zhang
 * @since 2023/8/24
 */
public class AccessTokenValidator implements Validator<AccessToken> {

    /**
     * 时间戳默认有效时长为 60秒.
     */
    private static final long TIMESTAMP_EXPIRATION_TIME = 60 * 1000L;

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

}
