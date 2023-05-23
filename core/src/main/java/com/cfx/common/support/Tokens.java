package com.cfx.common.support;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2023/5/8
 */
public abstract class Tokens {


    /**
     * The HTTP {@code Authorization} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.2">Section 4.2 of RF 7235</a>
     */
    public static final String AUTHORIZATION = "Authorization";
    public static final String SECURITY = "security";
    public static final String IP = "IP";
    public static final String BEARER = "Bearer ";
    public static final String USER_ID = "uid";
    public static final String APP_ID = "aid";
    public static final String USERNAME = "un";

    private Tokens() {
    }

    public static String getBearerToken(String token) {
        return BEARER + token;
    }

    public static String create(Long appid, Long userId, String username, String secret) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(USER_ID, userId);
        map.put(APP_ID, appid);
        map.put(USERNAME, username);

        return JWT.create(map, secret);
    }

    public static String create(Map<String, Object> payload, String secret) {
        return JWT.create(payload, secret);
    }


    public static Map<String, Claim> getClaims(String token, String secret) {
        return JWT.getClaims(token, secret);
    }

    public static String refresh(String token, String secret, boolean now) {

        DecodedJWT verify = JWT.verify(token, secret);

        Map<String, Claim> claims = verify.getClaims();

        if (now) {
            return JWT.create(claims, secret);
        }
        //过期前2分钟刷新
        Date expiresAt = verify.getExpiresAt();

        if (Dates.isExpired(expiresAt)) {
            claims.remove("exp");
            return JWT.create(claims, secret);
        } else
            return token;
    }


    public static class TokenConverter {

        public static final String APP_ID = "aid";
        public static final String USER_ID = "uid";
        public static final String USERNAME = "un";
        public static final String NICKNAME = "nn";
        public static final String PHONE = "p";
        public static final String EMAIL = "e";
        public static final String DEPT_ID = "di";
        public static final String DEPT_NAME = "dn";

        private final Map<String, Object> payload;

        private TokenConverter() {
            this.payload = new HashMap<>(8);
        }

        public static TokenConverter create() {
            return new TokenConverter();
        }

        public TokenConverter appid(Long appid) {
            this.payload.put(APP_ID, appid);
            return this;
        }

        public TokenConverter userId(Long userId) {
            this.payload.put(USER_ID, userId);
            return this;
        }

        public TokenConverter username(String username) {
            this.payload.put(USERNAME, username);
            return this;
        }

        public TokenConverter nickname(String nickname) {
            this.payload.put(NICKNAME, nickname);
            return this;
        }

        public TokenConverter phone(String phone) {
            this.payload.put(PHONE, phone);
            return this;
        }

        public TokenConverter email(String email) {
            this.payload.put(EMAIL, email);
            return this;
        }

        public TokenConverter deptId(Long deptId) {
            this.payload.put(DEPT_ID, deptId);
            return this;
        }

        public TokenConverter deptName(String deptName) {
            this.payload.put(DEPT_NAME, deptName);
            return this;
        }

        public Map<String, Object> build() {
            return this.payload;
        }
    }


    protected abstract static class Dates {

        /**
         * 跳跃到指定的时间之后 （单位：分钟）
         *
         * @param minute 分钟数
         */
        public static Date skip(int minute) {
            final Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE, minute);
            return instance.getTime();
        }

        /**
         * 判断是是否过期 误差为1分钟 一分钟之内都算过期
         *
         * @param expiresAt 时间类型
         */
        public static boolean isExpired(Date expiresAt) {
            final Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE, 2);
            Date date = instance.getTime();
            return expiresAt.compareTo(date) <= 0;
        }
    }

    protected abstract static class JWT {


        private static final String ISSUER = "cfx";

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
                    .withIssuer(ISSUER)
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
                        .withIssuer(ISSUER)
                        .build()
                        .verify(token);
            } catch (Exception e) {
                throw new JWTVerificationException(Errors.handleEx(e));
            }
        }
    }

    /**
     * 解析token相关异常
     */
    public abstract static class Errors {

        public static final String AlgorithmMismatch = "解析Token算法不匹配";
        public static final String SignatureVerification = "校验签名异常";
        public static final String TokenExpired = "Token过期";
        public static final String JWTDecode = "Token非法";
        public static final String ERROR = "解析Token异常";


        /**
         * 处理异常返回信息
         *
         * @param e 异常信息
         */
        protected static String handleEx(Exception e) {

            if (e instanceof AlgorithmMismatchException) {
                // 解析token算法不匹配
                return Errors.AlgorithmMismatch;
            } else if (e instanceof SignatureVerificationException) {
                // 签名异常
                return Errors.SignatureVerification;
            } else if (e instanceof TokenExpiredException) {
                // token过期
                return Errors.TokenExpired;
            } else if (e instanceof JWTDecodeException) {
                // token 非法
                return Errors.JWTDecode;
            } else {
                // 其他异常
                return Errors.ERROR;
            }
        }
    }


    enum TokenType {

        BEARER("Bearer");


        public final String type;

        TokenType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        private static final Map<String, TokenType> MAP = Arrays.stream(values()).collect(Collectors.toMap(TokenType::getType, Function.identity()));

        public static TokenType getInstance(String type) {
            return MAP.getOrDefault(type, TokenType.BEARER);
        }
    }

}
