package com.ziyao.harbor.crypto.utils;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.util.BigIntegers;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class BCUtils {
    /**
     * 转换为 ECPrivateKeyParameters
     *
     * @param d 私钥d值
     * @return ECPrivateKeyParameters
     */
    public static ECPrivateKeyParameters toSm2Params(String d) {
        return BCUtils.toSm2PrivateParams(d);
    }

    /**
     * 转换为 ECPrivateKeyParameters
     *
     * @param d 私钥d值
     * @return ECPrivateKeyParameters
     */
    public static ECPrivateKeyParameters toSm2Params(byte[] d) {
        return ECKeyUtils.toSm2PrivateParams(d);
    }


    /**
     * 构建ECDomainParameters对象
     *
     * @param x9ECParameters {@link X9ECParameters}
     * @return {@link ECDomainParameters}
     */
    public static ECDomainParameters toDomainParams(X9ECParameters x9ECParameters) {
        return new ECDomainParameters(
                x9ECParameters.getCurve(),
                x9ECParameters.getG(),
                x9ECParameters.getN(),
                x9ECParameters.getH()
        );
    }


    /**
     * 转换为SM2的ECPublicKeyParameters
     *
     * @param xHex 公钥X
     * @param yHex 公钥Y
     * @return ECPublicKeyParameters
     */
    public static ECPublicKeyParameters toSm2Params(String xHex, String yHex) {
        return BCUtils.toSm2PublicParams(xHex, yHex);
    }

    /**
     * 转换为SM2的ECPublicKeyParameters
     *
     * @param xBytes 公钥X
     * @param yBytes 公钥Y
     * @return ECPublicKeyParameters
     */
    public static ECPublicKeyParameters toSm2Params(byte[] xBytes, byte[] yBytes) {
        return ECKeyUtils.toSm2PublicParams(xBytes, yBytes);
    }

    /**
     * 转换为 ECPrivateKeyParameters
     *
     * @param d 私钥d值16进制字符串
     * @return ECPrivateKeyParameters
     */
    public static ECPrivateKeyParameters toSm2PrivateParams(String d) {
        return toPrivateParams(d, SmUtils.SM2_DOMAIN_PARAMS);
    }

    /**
     * 转换为 ECPrivateKeyParameters
     *
     * @param d                私钥d值16进制字符串
     * @param domainParameters ECDomainParameters
     * @return ECPrivateKeyParameters
     */
    public static ECPrivateKeyParameters toPrivateParams(String d, ECDomainParameters domainParameters) {
        if (null == d) {
            return null;
        }
        return toPrivateParams(BigIntegers.fromUnsignedByteArray(SecureUtils.decode(d)), domainParameters);
    }

    /**
     * 转换为SM2的ECPublicKeyParameters
     *
     * @param x 公钥X
     * @param y 公钥Y
     * @return ECPublicKeyParameters
     */
    public static ECPublicKeyParameters toSm2PublicParams(String x, String y) {
        return toPublicParams(x, y, SmUtils.SM2_DOMAIN_PARAMS);
    }

    /**
     * 转换为ECPublicKeyParameters
     *
     * @param x                公钥X
     * @param y                公钥Y
     * @param domainParameters ECDomainParameters
     * @return ECPublicKeyParameters，x或y为{@code null}则返回{@code null}
     */
    public static ECPublicKeyParameters toPublicParams(String x, String y, ECDomainParameters domainParameters) {
        return toPublicParams(SecureUtils.decode(x), SecureUtils.decode(y), domainParameters);
    }

    /**
     * 转换为ECPublicKeyParameters
     *
     * @param xBytes           公钥X
     * @param yBytes           公钥Y
     * @param domainParameters ECDomainParameters曲线参数
     * @return ECPublicKeyParameters
     */
    public static ECPublicKeyParameters toPublicParams(byte[] xBytes, byte[] yBytes, ECDomainParameters domainParameters) {
        if (null == xBytes || null == yBytes) {
            return null;
        }
        return toPublicParams(BigIntegers.fromUnsignedByteArray(xBytes), BigIntegers.fromUnsignedByteArray(yBytes), domainParameters);
    }

    /**
     * 转换为ECPublicKeyParameters
     *
     * @param x                公钥X
     * @param y                公钥Y
     * @param domainParameters ECDomainParameters
     * @return ECPublicKeyParameters
     */
    public static ECPublicKeyParameters toPublicParams(BigInteger x, BigInteger y, ECDomainParameters domainParameters) {
        if (null == x || null == y) {
            return null;
        }
        final ECCurve curve = domainParameters.getCurve();
        return toPublicParams(curve.createPoint(x, y), domainParameters);
    }

    /**
     * 转换为ECPublicKeyParameters
     *
     * @param point            曲线坐标点
     * @param domainParameters ECDomainParameters
     * @return ECPublicKeyParameters
     */
    public static ECPublicKeyParameters toPublicParams(org.bouncycastle.math.ec.ECPoint point, ECDomainParameters domainParameters) {
        return new ECPublicKeyParameters(point, domainParameters);
    }

    /**
     * 转换为 ECPrivateKeyParameters
     *
     * @param d                私钥d值
     * @param domainParameters ECDomainParameters
     * @return ECPrivateKeyParameters
     */
    public static ECPrivateKeyParameters toPrivateParams(BigInteger d, ECDomainParameters domainParameters) {
        if (null == d) {
            return null;
        }
        return new ECPrivateKeyParameters(d, domainParameters);
    }

    /**
     * 私钥转换为 {@link ECPrivateKeyParameters}
     *
     * @param privateKey 私钥，传入null返回null
     * @return {@link ECPrivateKeyParameters}或null
     */
    public static ECPrivateKeyParameters toParams(PrivateKey privateKey) {
        return ECKeyUtils.toPrivateParams(privateKey);
    }

    /**
     * 公钥转换为 {@link ECPublicKeyParameters}
     *
     * @param publicKey 公钥，传入null返回null
     * @return {@link ECPublicKeyParameters}或null
     */
    public static ECPublicKeyParameters toParams(PublicKey publicKey) {
        return ECKeyUtils.toPublicParams(publicKey);
    }
}
