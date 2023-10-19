package com.ziyao.harbor.crypto.asymmetric;

import com.ziyao.harbor.core.utils.Arrays;
import com.ziyao.harbor.crypto.AbstractAlgorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.Serial;
import java.io.Serializable;
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


    public Digester(String algorithm) {
        super(algorithm, null);
        try {
            this.digest = MessageDigest.getInstance(algorithm, new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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