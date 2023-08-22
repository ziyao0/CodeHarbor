package com.ziyao.cfx.common.token;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.ziyao.cfx.common.exception.ServiceException;
import com.ziyao.cfx.common.jwt.Jwts;
import com.ziyao.cfx.common.utils.Dates;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        return Jwts.create(map, secret);
    }

    /**
     * 解析token
     */
    public static String extractFromToken(String token) {
        // 遍历提取token
        for (TokenType tokenType : TokenType.values()) {

            if (token.startsWith(tokenType.getType())) {
                return token.replace(tokenType.getType(), "");
            }
        }
        throw new ServiceException();
    }

    public static String create(Map<String, Object> payload, String secret) {
        return Jwts.create(payload, secret);
    }


    public static Map<String, Claim> getClaims(String token, String secret) {
        return Jwts.getClaims(token, secret);
    }

    public static String refresh(String token, String secret, boolean now) {

        DecodedJWT verify = Jwts.verify(token, secret);

        Map<String, Claim> claims = verify.getClaims();

        if (now) {
            return Jwts.create(claims, secret);
        }
        //过期前2分钟刷新
        Date expiresAt = verify.getExpiresAt();

        if (Dates.isExpired(expiresAt)) {
            claims.remove("exp");
            return Jwts.create(claims, secret);
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
}
