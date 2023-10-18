package com.ziyao.harbor.crypto;

/**
 * @author ziyao zhang
 * @since 2023/10/18
 */
public enum Padding {
    /**
     * 无补码
     */
    NoPadding,
    /**
     * 0补码，即不满block长度时使用0填充
     */
    ZeroPadding,
    /**
     * W3C 的“XML 加密语法和处理”文档中的 5.2 块加密算法中描述了块密码的此填充。
     */
    ISO10126Padding,
    /**
     * PKCS1 中定义的最佳非对称加密填充方案
     */
    OAEPPadding,
    /**
     * PKCS 1 中描述的填充方案，与 RSA 算法一起使用
     */
    PKCS1Padding,
    /**
     * RSA 实验室“PKCS 5：基于密码的加密标准”1.5 版（1993 年 11 月）中描述的填充方案。
     */
    PKCS5Padding,
    /**
     * SSL 协议版本 3.0（1996 年 11 月 18 日）第 5.2.3.2 节（CBC 分组密码）中定义的填充方案
     */
    SSL3Padding
}
