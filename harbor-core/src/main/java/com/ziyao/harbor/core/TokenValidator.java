package com.ziyao.harbor.core;

/**
 * @author ziyao zhang
 * @since 2023/9/14
 */
public interface TokenValidator<V> {


    /**
     * 判断给定字符串是否是当前 {@link TokenGenerator} 生成的令牌格式.
     *
     * @param text 给定字符串
     */
    void validate(String text) throws Exception;

    /**
     * 判断给定字符串是否是当前 {@link TokenGenerator} 生成的令牌格式.
     *
     * @param text 给定字符串
     * @return true 给定字符串符合令牌格式
     */
    boolean isTokenFormat(String text);

    /**
     * 获取Token携带的数据
     *
     * @param text 给定字符串
     * @return {@link V} 返回令牌携带的信息
     */
    V loadTokenDetails(String text) throws Exception;
}
