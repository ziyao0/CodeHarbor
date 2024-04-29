package com.ziyao.oauth2.core;

import com.ziyao.harbor.core.utils.Strings;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2024/3/27
 */
@Getter
public enum TokenType {
    /**
     * 浏览器token前缀
     */
    Bearer("Bearer"),
    /**
     * 刷新Token
     */
    Refresh("Refresh"),
    ;

    public final String type;

    TokenType(String type) {
        this.type = type;
    }

    private static final List<TokenType> list;
    private static final Map<String, TokenType> TOKEN_TYPE_MAP;

    static {
        list = Arrays.stream(values()).collect(Collectors.toList());
        TOKEN_TYPE_MAP = Arrays.stream(values()).collect(Collectors.toMap(TokenType::getType, Function.identity()));
    }

    public static List<TokenType> getTokens() {
        return list;
    }

    /**
     * 提取token
     *
     * @param token token值
     * @return token
     */
    public static String extract(String token) {
        for (TokenType tokenType : getTokens()) {
            if (token.startsWith(tokenType.getType())) {
                return token.substring(tokenType.getType().length()).trim();
            }
        }
        return null;
    }

    /**
     * 获取token头
     *
     * @param token token
     * @return {@link TokenType}
     */
    public static TokenType getInstance(String token) {
        if (Strings.hasLength(token)) {
            for (TokenType tokenType : getTokens()) {
                if (token.startsWith(tokenType.getType())) {
                    return tokenType;
                }
            }
        }
        throw new IllegalArgumentException("Illegal token " + token);
    }
}
