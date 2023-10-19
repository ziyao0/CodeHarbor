package com.ziyao.harbor.crypto.utils;

import com.ziyao.harbor.core.utils.Arrays;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Hexes;
import com.ziyao.harbor.crypto.Algorithm;
import com.ziyao.harbor.crypto.KeyPair;
import com.ziyao.harbor.crypto.asymmetric.SM2;
import com.ziyao.harbor.crypto.asymmetric.SM3;
import com.ziyao.harbor.crypto.exception.CryptoException;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.signers.StandardDSAEncoding;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigInteger;

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
     * SM2推荐曲线参数（来自https://github.com/ZZMarquis/gmhelper）
     */
    public static final ECDomainParameters SM2_DOMAIN_PARAMS = BCUtils.toDomainParams(GMNamedCurves.getByName(SM2_CURVE_NAME));
    /**
     * SM2国密算法公钥参数的Oid标识
     */
    public static final ASN1ObjectIdentifier ID_SM2_PUBLIC_KEY_PARAM = new ASN1ObjectIdentifier("1.2.156.10197.1.301");


    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
     *
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     * @since 4.5.0
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
        // for sm2p256v1, n is 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
        // r and s are the result of mod n, so they should be less than n and have length<=32
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
        KeyPair keyPair = generateSm2KeyPair();
        SM2 sm2 = createSm2(keyPair);
        byte[] encrypt = sm2.encrypt("張i主要".getBytes());
        String string = Hexes.encodeHexStr(encrypt);
        System.out.println(string);
        System.out.println(Hexes.encodeHex(encrypt));
        System.out.println(new String(sm2.decrypt(Hexes.decodeHex(string))));
    }

    public static KeyPair generateSm2KeyPair() {
        java.security.KeyPair keyPair = Keys.generateKeyPair(Algorithm.SM2);
        BCECPrivateKey priKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey pubKey = (BCECPublicKey) keyPair.getPublic();

        String publicKey = Hexes.encodeHexStr(pubKey.getQ().getEncoded(false));
        String privateKey = Hexes.encodeHexStr(priKey.getD().toByteArray());
        return new KeyPair(Algorithm.SM2, Hexes.getDecoder(true), publicKey, privateKey);
    }

    /**
     * 创建{@link SM2}
     *
     * @param keyPair {@link KeyPair}
     * @return sm2对象
     */
    public static SM2 createSm2(KeyPair keyPair) {
        Assert.notNull(keyPair, "keyPair 不能为空！");
        return new SM2(keyPair.getDecodePrivateKey(), keyPair.getDecodePublicKey());
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
}
