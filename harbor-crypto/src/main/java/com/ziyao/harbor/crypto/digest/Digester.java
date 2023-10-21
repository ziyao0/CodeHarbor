package com.ziyao.harbor.crypto.digest;

import com.ziyao.harbor.core.utils.Arrays;
import com.ziyao.harbor.core.utils.Charsets;
import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.AbstractAlgorithm;
import com.ziyao.harbor.crypto.asymmetric.DigestAlgorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public class Digester extends AbstractAlgorithm implements Serializable {

    @Serial
    private static final long serialVersionUID = -5710575887725018089L;
    /**
     * 代理的 JDK {@link MessageDigest} 实现.
     */
    private final MessageDigest digest;
    /**
     * 盐值
     */
    protected byte[] salt;
    /**
     * 加盐位置，即将盐值字符串放置在数据的index数，默认0
     */
    protected int saltPosition;
    /**
     * 散列次数
     */
    protected int digestCount;

    public Digester(String algorithm) {
        super(algorithm);
        try {
            this.digest = MessageDigest.getInstance(algorithm, new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Digester(DigestAlgorithm digestAlgorithm) {
        super(digestAlgorithm.getValue());
        try {
            this.digest = MessageDigest.getInstance(digestAlgorithm.getValue(), new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成摘要，并转为16进制字符串<br>
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digestHex(byte[] data) {
        return HexUtils.encodeHexStr(digest(data));
    }

    /**
     * 生成文件摘要
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digestHex(String data) {
        return digestHex(data, Charsets.UTF_8);
    }

    /**
     * 生成文件摘要，并转为16进制字符串
     *
     * @param data        被摘要数据
     * @param charsetName 编码
     * @return 摘要
     */
    public String digestHex(String data, String charsetName) {
        return digestHex(data, Charsets.forName(charsetName));
    }

    /**
     * 生成文件摘要，并转为16进制字符串
     *
     * @param data    被摘要数据
     * @param charset 编码
     * @return 摘要
     */
    public String digestHex(String data, Charset charset) {
        return HexUtils.encodeHexStr(digest(data, charset));
    }

    /**
     * 生成文件摘要
     *
     * @param data    被摘要数据
     * @param charset 编码
     * @return 摘要
     */
    public byte[] digest(String data, Charset charset) {
        return digest(Strings.toBytes(data, charset));
    }

    public byte[] digest() {
        return digest.digest();
    }

    public byte[] digest(byte[] input) {
        if (Arrays.isEmpty(input)) {
            return new byte[0];
        } else {
            return digest.digest(input);
        }
    }
}