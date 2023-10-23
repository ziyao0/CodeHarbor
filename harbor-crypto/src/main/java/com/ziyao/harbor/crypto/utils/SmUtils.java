package com.ziyao.harbor.crypto.utils;

import com.ziyao.harbor.core.codec.HexCodec;
import com.ziyao.harbor.core.codec.StringCodec;
import com.ziyao.harbor.core.utils.Arrays;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.HexUtils;
import com.ziyao.harbor.core.utils.Randoms;
import com.ziyao.harbor.crypto.Algorithm;
import com.ziyao.harbor.crypto.BytesCipher;
import com.ziyao.harbor.crypto.DefaultTextCipher;
import com.ziyao.harbor.crypto.TextCipher;
import com.ziyao.harbor.crypto.asymmetric.KeyPair;
import com.ziyao.harbor.crypto.asymmetric.SM2;
import com.ziyao.harbor.crypto.asymmetric.Sm2BytesCipher;
import com.ziyao.harbor.crypto.digest.SM3;
import com.ziyao.harbor.crypto.exception.CryptoException;
import com.ziyao.harbor.crypto.symmetric.*;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class SmUtils {

    private final static int RS_LEN = 32;
    /**
     * SM2默认曲线
     */
    public static final String SM2_CURVE_NAME = "sm2p256v1";
    /**
     * SM2推荐曲线参数
     * <p>
     * <a href="https://github.com/ZZMarquis/gmhelper">来自</a>
     */
    public static final ECDomainParameters SM2_DOMAIN_PARAMS = BCUtils.toDomainParams(GMNamedCurves.getByName(SM2_CURVE_NAME));
    /**
     * SM2国密算法公钥参数的Oid标识
     */
    public static final ASN1ObjectIdentifier ID_SM2_PUBLIC_KEY_PARAM = new ASN1ObjectIdentifier("1.2.156.10197.1.301");

    /**
     * 创建{@link SM2}
     *
     * @param keyPair {@link KeyPair}
     * @return sm2对象
     */
    public static SM2 createSm2(KeyPair keyPair) {
        Assert.notNull(keyPair, "keyPair 不能为空！");
        return new SM2(keyPair.getPrivateKey(), keyPair.getPublicKey());
    }

    /**
     * 创建{@link SM2}
     *
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return sm2对象
     */
    public static SM2 createSm2(String privateKey, String publicKey) {
        Assert.notNull(privateKey, "privateKey 不能为空！");
        Assert.notNull(publicKey, "publicKey 不能为空！");
        return new SM2(privateKey, publicKey);
    }

    /**
     * 创建{@link SM2}
     *
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return sm2对象
     */
    public static SM2 createSm2(byte[] privateKey, byte[] publicKey) {
        Assert.notNull(privateKey, "privateKey 不能为空！");
        Assert.notNull(publicKey, "publicKey 不能为空！");
        return new SM2(privateKey, publicKey);
    }

    /**
     * SM3加密<br>
     * 例：<br>
     * SM3加密：sm3().digest(data)<br>
     * SM3加密并转为16进制字符串：sm3().digestHex(data)<br>
     *
     * @return {@link SM3}
     */
    public static SM3 createSm3() {
        return new SM3();
    }

    /**
     * Sm4加密
     *
     * @return {@link SM4}
     */
    public static SM4 createSm4() {
        return new SM4();
    }


    /**
     * 获取公私钥
     *
     * @return {@link KeyPair}
     */
    public static KeyPair generateSm2KeyPair() {
        java.security.KeyPair keyPair = KeyUtils.generateKeyPair(Algorithm.SM2);
        BCECPrivateKey priKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey pubKey = (BCECPublicKey) keyPair.getPublic();

        byte[] publicKey = pubKey.getQ().getEncoded(false);
        byte[] privateKey = priKey.getD().toByteArray();
        return new KeyPair(Algorithm.SM2, publicKey, privateKey);
    }

    public static KeyIv generateSm4KeyIv() {
        SecretKey secretKey = KeyUtils.generateKey(Algorithm.SM4, 128);
        byte[] iv = new byte[16];
        SecureRandom secureRandom = Randoms.getSecureRandom();
        secureRandom.nextBytes(iv);
        return new KeyIv(Algorithm.SM4, secretKey.getEncoded(), iv);
    }

    /**
     * sm2文本密码
     *
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return 返回文本密码
     */
    public static TextCipher createSm2TextCipher(String privateKey, String publicKey) {
        Assert.notNull(privateKey, "私钥不能为空！");
        Assert.notNull(publicKey, "公钥不能为空！");
        BytesCipher bytesCipher = createSm2BytesCipher(privateKey, publicKey);
        return new DefaultTextCipher(bytesCipher, HexCodec.CODEC);
    }

    /**
     * 创建sm4文本密码
     *
     * @param key 密钥
     * @param iv  向量 加盐
     * @return {@link TextCipher}
     */
    public static TextCipher createSm4CBCTextCipherWithZeroPaddingAndHexCodec(String key, String iv) {
        Assert.notNull(key, "密钥不能为空！");
        Assert.notNull(iv, "向量不能为空！");
        SM4 sm4 = createSm4CBCWithZeroPadding(key, iv);
        BytesCipher bytesCipher = createSm4BytesCipher(sm4);
        return new DefaultTextCipher(bytesCipher, HexCodec.CODEC);
    }

    /**
     * 创建sm4文本密码
     *
     * @param keyIv 密钥向量
     * @return {@link TextCipher}
     */
    public static TextCipher createSm4CBCTextCipherWithZeroPaddingAndHexCodec(KeyIv keyIv) {
        Assert.notNull(keyIv, "密钥不能为空！");
        Assert.notNull(keyIv.getKey(), "密钥不能为空！");
        Assert.notNull(keyIv.getIv(), "向量不能为空！");
        SM4 sm4 = createSm4CBCWithZeroPadding(keyIv.getKey(), keyIv.getIv());
        BytesCipher bytesCipher = createSm4BytesCipher(sm4);
        return new DefaultTextCipher(bytesCipher, HexCodec.CODEC);
    }

    /**
     * 创建sm4文本密码
     *
     * @param key 密钥
     * @param iv  向量 加盐
     * @return {@link TextCipher}
     */
    public static TextCipher createSm4ECBTextCipherWithZeroPaddingAndHexCodec(String key, String iv) {
        Assert.notNull(key, "密钥不能为空！");
        Assert.notNull(iv, "向量不能为空！");
        SM4 sm4 = createSm4WithZeroPadding(Mode.ECB, key, iv);
        BytesCipher bytesCipher = createSm4BytesCipher(sm4);
        return new DefaultTextCipher(bytesCipher, HexCodec.CODEC);
    }

    /**
     * 创建sm4文本密码
     *
     * @param key 密钥
     * @param iv  向量 加盐
     * @return {@link TextCipher}
     */
    public static TextCipher createSm4CBCTextCipherWithPKCS5PaddingAndHexCodec(String key, String iv) {
        Assert.notNull(key, "密钥不能为空！");
        Assert.notNull(iv, "向量不能为空！");
        SM4 sm4 = createSm4CBCWithPKCS5Padding(key, iv);
        BytesCipher bytesCipher = createSm4BytesCipher(sm4);
        return new DefaultTextCipher(bytesCipher, HexCodec.CODEC);
    }

    private static BytesCipher createSm4BytesCipher(SM4 sm4) {
        return new Sm4BytesCipher(sm4);
    }

    private static BytesCipher createSm2BytesCipher(SM2 sm2) {
        return new Sm2BytesCipher(sm2);
    }

    private static BytesCipher createSm2BytesCipher(String privateKey, String publicKey) {
        return new Sm2BytesCipher(HexCodec.CODEC.decode(privateKey), HexCodec.CODEC.decode(publicKey));
    }

    public static SM4 createSm4CBCWithZeroPadding(String key, String iv) {
        return createSm4WithZeroPadding(Mode.CBC, key, iv);
    }

    public static SM4 createSm4WithZeroPadding(Mode mode, String key, String iv) {
        return createSm4(mode, Padding.ZeroPadding, key, iv);
    }

    public static SM4 createSm4(Mode mode, Padding padding, String key, String iv) {
        return new SM4(mode, padding, key, iv);
    }

    public static SM4 createSm4CBCWithPKCS5Padding(String key, String iv) {
        return new SM4(Mode.CBC, Padding.PKCS5Padding, HexUtils.decodeHex(key), HexUtils.decodeHex(iv));
    }

    private static TextCipher createTextCipher(BytesCipher bytesCipher, StringCodec codec) {
        return new DefaultTextCipher(bytesCipher, codec);
    }

    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
     *
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    public static byte[] rsAsn1ToPlain(byte[] rsDer) {
        final BigInteger[] decode;
        try {
            decode = StandardDSAEncoding.INSTANCE.decode(SM2_DOMAIN_PARAMS.getN(), rsDer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final byte[] r = bigIntToFixedLengthBytes(decode[0]);
        final byte[] s = bigIntToFixedLengthBytes(decode[1]);

        return Arrays.addAll(r, s);
    }

    /**
     * BigInteger转固定长度bytes
     *
     * @param rOrS {@link BigInteger}
     * @return 固定长度bytes
     */
    private static byte[] bigIntToFixedLengthBytes(BigInteger rOrS) {
        byte[] rs = rOrS.toByteArray();
        if (rs.length == RS_LEN) {
            return rs;
        } else if (rs.length == RS_LEN + 1 && rs[0] == 0) {
            return org.bouncycastle.util.Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        } else if (rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            org.bouncycastle.util.Arrays.fill(result, (byte) 0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            throw new CryptoException("Error rs: " + Hex.toHexString(rs));
        }
    }


    public static void main(String[] args) {

        KeyIv keyIv = generateSm4KeyIv();
        TextCipher sm4CBCTextCipherWithZeroPadding = createSm4CBCTextCipherWithZeroPaddingAndHexCodec(keyIv.getKey(), keyIv.getIv());
        String encrypt1 = sm4CBCTextCipherWithZeroPadding.encrypt("这个那");
        System.out.println(encrypt1);
        System.out.println(sm4CBCTextCipherWithZeroPadding.decrypt(encrypt1));

        TextCipher sm4CBCTextCipherWithPKCS5Padding = createSm4CBCTextCipherWithPKCS5PaddingAndHexCodec(keyIv.getKey(), keyIv.getIv());
        String encrypt2 = sm4CBCTextCipherWithPKCS5Padding.encrypt("张子尧");
        System.out.println(sm4CBCTextCipherWithZeroPadding.decrypt(encrypt2));
    }

}

