package com.ziyao.harbor.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ziyao.harbor.core.error.Errors;
import com.ziyao.harbor.core.error.exception.HarborException;
import com.ziyao.harbor.core.token.JwtInfo;
import com.ziyao.harbor.core.token.TokenDetails;
import com.ziyao.harbor.core.utils.Dates;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public abstract class Jwts {

    private Jwts() {
    }

    /**
     * 创建token
     *
     * @param details Token详情
     * @return token
     */
    public static String create(TokenDetails details) {
        JWTCreator.Builder builder = JWT.create();
        // 填充头部信息
        // @formatter:off
        TokenDetails.Header header = details.getHeader();
        if (Objects.nonNull(header)) {
            builder.withIssuer(header.getIssuer())
                    .withSubject(header.getSubject())
                    .withAudience(header.getAudience());
        }
        return builder.withPayload(details.getPayload())
                .withExpiresAt(details.getExpiresAt())
                .sign(Algorithm.HMAC256(details.getSecret()));
        // @formatter:on
    }

    public static String refresh(String jwt, String secret) {
        DecodedJWT decodedJWT = verify(jwt, secret);
        Date expiresAt = decodedJWT.getExpiresAt();
        if (Dates.isExpired(expiresAt, 5)) {
            String header = decodedJWT.getHeader();
            System.out.println(header);
            Map<String, Claim> claims = new HashMap<>(decodedJWT.getClaims());

            claims.remove("key");
           claims.remove("exp");
            String sign = JWT.create().withPayload(claims).withExpiresAt(Dates.skip(20)).sign(Algorithm.HMAC256(secret));
            System.out.println(sign);
            return sign;
        }
        return jwt;
    }


    public static void main(String[] args) {
        JwtInfo build = JwtInfo.builder().payload(new HashMap<>() {
            {
                put("key", "value");
                put("key1", "value1");
            }
        }).expiresAt(Dates.skip(3)).secret("zhangziyao").jwtHeader(new JwtInfo.JwtHeader("1","2","1")).build();
        String s = create(build);

        String zhangziyao = refresh(s, "zhangziyao");
        System.out.println(zhangziyao);
    }
    public static String create(Map<String, ?> payload, String secret, Date expr) {
        return com.auth0.jwt.JWT.create()
                .withIssuer("ISSUER")
                .withPayload(payload)
                .withExpiresAt(expr)
                .sign(Algorithm.HMAC256(secret));
    }

    public static String create(Map<String, ?> payload, String secret) {
        return create(payload, secret, Dates.skip(30));
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
                    .build()
                    .verify(token);
        } catch (Exception e) {
            if (e instanceof AlgorithmMismatchException) {
                // 解析token算法不匹配
                throw new HarborException(Errors.ALGORITHM_MISMATCH);
            } else if (e instanceof SignatureVerificationException) {
                // 签名异常
                throw new HarborException(Errors.SIGNATURE_VERIFICATION);
            } else if (e instanceof TokenExpiredException) {
                // token过期
                throw new HarborException(Errors.TOKEN_EXPIRED);
            } else if (e instanceof JWTDecodeException) {
                // token 非法
                throw new HarborException(Errors.JWT_DECODE);
            } else {
                // 解析token异常
                throw new HarborException(Errors.TOKEN_ERROR);
            }
        }
    }

}
