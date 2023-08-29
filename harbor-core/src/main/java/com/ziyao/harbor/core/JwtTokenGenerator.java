package com.ziyao.harbor.core;

import com.auth0.jwt.interfaces.Claim;
import com.ziyao.harbor.core.jwt.Jwts;
import com.ziyao.harbor.core.token.JwtInfo;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.core.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/8/29
 */
public class JwtTokenGenerator implements TokenGenerator<JwtInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenGenerator.class);

    @Override
    public String generateToken(JwtInfo jwtInfo) {
        String token = Jwts.create(jwtInfo);
        return jwtInfo.getTokenType().getType() + token;
    }

    @Override
    public boolean validate(String text, String secret) {
        Assert.notNull(text, "缺少Token认证信息");
        return doValidate(text, secret);
    }

    private boolean doValidate(String text, String secret) {
        try {
            Jwts.verify(TokenType.extract(text), secret);
            return true;
        } catch (Exception e) {
            LOGGER.error("token校验失败：{}", e.getMessage());
        }
        return false;
    }

    @Override
    public Map<String, Claim> getTokenDetails(String text, String secret) {
        return Jwts.getClaims(TokenType.extract(text), secret);
    }
}
