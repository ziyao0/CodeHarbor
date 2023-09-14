package com.ziyao.harbor.core;

import com.ziyao.harbor.core.token.TokenDetails;

/**
 * @author ziyao zhang
 * @since 2023/8/29
 */
public interface TokenGenerator<T extends TokenDetails> {

    /**
     * 根据指定令牌类型生成对应令牌.
     *
     * @param t 令牌信息
     * @return 令牌
     */
    String generateToken(T t);

}
