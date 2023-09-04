package com.ziyao.harbor.core.error;

import com.ziyao.harbor.core.error.exception.HarborException;

/**
 * @author ziyao zhang
 * @since 2023/9/4
 */
public abstract class HarborExceptions {

    public HarborExceptions() {
    }

    /**
     * 创建请求未认证异常
     *
     * @param message 异常信息
     * @return {@link HarborException}
     */
    public static HarborException createUnauthorizedException(String message) {
        return new HarborException(401, message);
    }

    /**
     * 创建越权访问异常
     *
     * @param message 异常信息
     * @return {@link HarborException}
     */
    public static HarborException createForbiddenException(String message) {
        return new HarborException(403, message);
    }
}
