package com.ziyao.harbor.common.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ziyao.harbor.common.utils.Dates;

import java.util.Date;
import java.util.Map;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class Jwts {

    private static final String AlgorithmMismatch = "解析Token算法不匹配";
    private static final String SignatureVerification = "校验签名异常";
    private static final String TokenExpired = "Token过期";
    private static final String JWTDecode = "Token非法";
    private static final String ERROR = "解析Token异常";

    private Jwts() {
    }

    /**
     * 创建token
     *
     * @param secret 密钥
     */
    public static String create(Map<String, ?> payload, String secret) {
        return create(payload, secret, Dates.skip(30));
    }

    /**
     * 创建token
     *
     * @param secret 密钥
     * @return token
     */
    public static String create(Map<String, ?> payload, String secret, Date expr) {
        return com.auth0.jwt.JWT.create()
                .withIssuer("ISSUER")
                .withPayload(payload)
                .withExpiresAt(expr)
                .sign(Algorithm.HMAC256(secret));
    }


    /**
     * 验证token
     *
     * @param token  token
     * @param secret 密钥
     * @return 验证结果
     */
    public static Map<String, Claim> getClaims(String token, String secret) {
        return verify(token, secret).getClaims();
    }

    /**
     * 验证token
     *
     * @param token  token
     * @param secret 密钥
     * @return 验证结果
     */
    public static DecodedJWT verify(String token, String secret) {
        try {
            return com.auth0.jwt.JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("ISSUER")
                    .build()
                    .verify(token);
        } catch (Exception e) {
            if (e instanceof AlgorithmMismatchException) {
                // 解析token算法不匹配
                throw new JWTVerificationException(AlgorithmMismatch);
            } else if (e instanceof SignatureVerificationException) {
                // 签名异常
                throw new JWTVerificationException(SignatureVerification);
            } else if (e instanceof TokenExpiredException) {
                // token过期
                throw new JWTVerificationException(TokenExpired);
            } else if (e instanceof JWTDecodeException) {
                // token 非法
                throw new JWTVerificationException(JWTDecode);
            } else {
                // 其他异常
                throw new JWTVerificationException(ERROR);
            }


        }
    }

}
