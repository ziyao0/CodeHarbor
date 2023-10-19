package com.ziyao.harbor.crypto.utils;

import com.ziyao.harbor.core.codec.Base64;
import com.ziyao.harbor.core.lang.CodeValidator;
import com.ziyao.harbor.core.utils.Hexes;
import com.ziyao.harbor.crypto.GlobalBouncyCastleProvider;
import com.ziyao.harbor.crypto.exception.CryptoException;

import javax.crypto.Cipher;
import java.security.Provider;
import java.security.Security;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class Secures {


    /**
     * 增加加密解密的算法提供者，默认优先使用，例如：
     *
     * <pre>
     * addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
     * </pre>
     *
     * @param provider 算法提供者
     * @since 4.1.22
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
     * @since 4.3.3
     */
    public static byte[] decode(String key) {
        return CodeValidator.isHex(key) ? Hexes.decodeHex(key) : Base64.decode(key);
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
}
