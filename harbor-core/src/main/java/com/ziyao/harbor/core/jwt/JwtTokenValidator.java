package com.ziyao.harbor.core.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.ziyao.harbor.core.TokenValidator;
import com.ziyao.harbor.core.error.HarborExceptions;
import com.ziyao.harbor.core.token.TokenType;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/9/14
 */
public class JwtTokenValidator implements TokenValidator<Map<String, Claim>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenValidator.class);

    private final String secret;

    public JwtTokenValidator(String secret) {
        this.secret = secret;
    }

    @Override
    public void validate(String text) {
        Assert.notNull(text, "缺少Token认证信息");
        String extract = TokenType.extract(text);
        if (Strings.hasLength(text)) {
            doValidate(extract, secret);
        } else {
            throw HarborExceptions.createIllegalArgumentException("缺少Token认证信息");
        }
    }

    @Override
    public boolean isTokenFormat(String text) {
        String extract = TokenType.extract(text);
        return Strings.hasLength(extract);
    }

    @Override
    public Map<String, Claim> loadTokenDetails(String text) {
        Assert.notNull(text, "缺少Token认证信息");
        String extract = TokenType.extract(text);
        return Jwts.getClaims(extract, secret);
    }

    private void doValidate(String jwt, String secret) {
        Jwts.verify(jwt, secret);
    }
}
