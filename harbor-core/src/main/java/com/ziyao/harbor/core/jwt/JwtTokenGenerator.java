package com.ziyao.harbor.core.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.ziyao.harbor.core.TokenGenerator;
import com.ziyao.harbor.core.token.JwtInfo;

/**
 * jwt token生成器
 * <p>
 * 基于auth0 java-jwt生成jwt令牌信息
 * {@link JwtInfo}包含头部、载体、秘钥等相关信息
 * 临牌生成算法为{@link Algorithm#HMAC256(byte[])}
 *
 * @author ziyao zhang
 * @since 2023/8/29
 */
public class JwtTokenGenerator implements TokenGenerator<JwtInfo> {


    @Override
    public String generateToken(JwtInfo jwtInfo) {
        String token = Jwts.create(jwtInfo);
        return jwtInfo.getTokenType().getType() + token;
    }
}
