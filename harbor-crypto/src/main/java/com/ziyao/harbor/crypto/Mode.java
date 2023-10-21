package com.ziyao.harbor.crypto;

/**
 * @author ziyao zhang
 * @since 2023/10/18
 */
public enum Mode {
    /**
     * 密码分组连接模式（Cipher Block Chaining）
     */
    CBC,
    /**
     * 密文反馈模式（Cipher Feedback）
     */
    CFB,
    /**
     * 计数器模式（A simplification of OFB）
     */
    CTR,
    /**
     * Cipher Text Stealing
     */
    CTS,
    /**
     * 电子密码本模式（Electronic CodeBook）
     */
    ECB,
    /**
     * 输出反馈模式（Output Feedback）
     */
    OFB,
    /**
     * 传播密码块
     */
    PCBC
}
