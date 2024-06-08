package com.ziyao.harbor.usercenter.authenticate.token.generator;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.usercenter.authenticate.token.OAuth2TokenContext;
import com.ziyao.security.oauth2.core.OAuth2Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/3/25
 */
@Getter
public final class DelegatingOAuth2TokenGenerator implements OAuth2TokenGenerator<OAuth2Token> {

    private final List<OAuth2TokenGenerator<OAuth2Token>> tokenGenerators;

    @SafeVarargs
    public DelegatingOAuth2TokenGenerator(OAuth2TokenGenerator<? extends OAuth2Token>... tokenGenerators) {
        Assert.notNull(tokenGenerators, "tokenGenerators cannot be empty");
        Assert.noNullElements(tokenGenerators, "tokenGenerator cannot be null");
        this.tokenGenerators = List.copyOf(asList(tokenGenerators));
    }

    @Override
    public OAuth2Token generate(OAuth2TokenContext context) {
        for (OAuth2TokenGenerator<OAuth2Token> tokenGenerator : this.tokenGenerators) {
            OAuth2Token token = tokenGenerator.generate(context);
            if (token != null) {
                return token;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static List<OAuth2TokenGenerator<OAuth2Token>> asList(
            OAuth2TokenGenerator<? extends OAuth2Token>... tokenGenerators) {

        List<OAuth2TokenGenerator<OAuth2Token>> tokenGeneratorList = new ArrayList<>();
        for (OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator : tokenGenerators) {
            tokenGeneratorList.add((OAuth2TokenGenerator<OAuth2Token>) tokenGenerator);
        }
        return tokenGeneratorList;
    }
}
