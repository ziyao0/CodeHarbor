package com.ziyao.harbor.core;

import com.ziyao.harbor.core.token.TokenDetails;

/**
 * @author ziyao zhang
 * @since 2023/8/29
 */
public interface TokenGenerator<T extends TokenDetails, R> {

    /**
     * 根据指定令牌类型生成对应令牌.
     *
     * @param t 令牌信息
     * @return 令牌
     */
    String generateToken(T t);

    /**
     * 判断给定字符串是否是当前 {@link TokenGenerator} 生成的令牌格式.
     *
     * @param text   给定字符串
     * @param secret 秘钥信息，jwt校验时需要
     * @return true 给定字符串符合令牌格式
     */
    boolean validate(String text, String secret);

    /**
     * 获取Token携带的数据
     *
     * @param text   给定字符串
     * @param secret 秘钥信息，jwt校验时需要
     * @return {@link R} 返回令牌携带的信息
     */
    default R getTokenDetails(String text, String secret) {
        return null;
    }


}
