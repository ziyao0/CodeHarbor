package com.ziyao.harbor.crypto.utils;

import com.ziyao.harbor.core.codec.Base64;
import com.ziyao.harbor.core.lang.CodeValidator;
import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.crypto.GlobalBouncyCastleProvider;
import com.ziyao.harbor.crypto.exception.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.Provider;
import java.security.Security;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class SecureUtils {


    /**
     * 增加加密解密的算法提供者，默认优先使用，例如：
     *
     * <pre>
     * addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
     * </pre>
     *
     * @param provider 算法提供者
     */
    public static void addProvider(Provider provider) {
        Security.insertProviderAt(provider, 0);
    }

    /**
     * 解码字符串密钥，可支持的编码如下：
     *
     * <pre>
     * 1. Hex（16进制）编码
     * 1. Base64编码
     * </pre>
     *
     * @param key 被解码的密钥字符串
     * @return 密钥
     */
    public static byte[] decode(String key) {
        return CodeValidator.isHex(key) ? HexUtils.decodeHex(key) : Base64.decode(key);
    }

    public static Cipher createCipher(String algorithm) {
        final Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();

        Cipher cipher;
        try {
            cipher = (null == provider) ? Cipher.getInstance(algorithm) : Cipher.getInstance(algorithm, provider);
        } catch (Exception e) {
            throw new CryptoException(e);
        }

        return cipher;
    }

    /**
     * 生成 {@link SecretKey}，仅用于对称加密和摘要算法密钥生成
     *
     * @param algorithm 算法
     * @param key       密钥，如果为{@code null} 自动生成随机密钥
     * @return {@link SecretKey}
     */
    public static SecretKey generateKey(String algorithm, byte[] key) {
        return KeyUtils.generateKey(algorithm, key);
    }

}
