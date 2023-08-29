package com.ziyao.harbor.core.token;

import com.ziyao.harbor.core.utils.CommUtils;
import com.ziyao.harbor.core.utils.Strings;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * token类型
 *
 * @author zhangziyao
 * @since 2023/4/23
 */
@Getter
public enum TokenType {
    /**
     * 浏览器token前缀
     */
    Bearer("Bearer "),
    /**
     * 客户端token前缀
     */
    Client("Client "),
    /**
     * 移动端token前缀
     */
    App("App "),
    /**
     * 刷新Token
     */
    Refresh("Refresh "),
    ;

    public final String type;

    TokenType(String type) {
        this.type = type;
    }

    private static final List<TokenType> list;
    private static final Map<String, TokenType> TOKEN_TYPE_MAP;

    static {
        list = Arrays.stream(values()).toList();
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
        for (TokenType tokenType : list) {
            if (token.startsWith(tokenType.getType())) {
                return token.replace(tokenType.getType(), CommUtils.EMPTY_CHAR);
            }
        }
        return null;
    }

    /**
     * 提取token
     *
     * @param token token值
     * @return token
     */
    public static TokenType extractHead(String token) {
        for (TokenType tokenType : list) {
            if (token.startsWith(tokenType.getType())) {
                return tokenType;
            }
        }
        throw new IllegalArgumentException("Unknown token type. token=" + token);
    }

    /**
     * 获取token头
     *
     * @param type type
     * @return {@link TokenType}
     */
    public static TokenType getInstance(String type) {
        if (Strings.hasLength(type)) {
            TokenType tokenType = TOKEN_TYPE_MAP.get(type);
            if (Objects.nonNull(tokenType)) {
                return tokenType;
            }
        }
        throw new IllegalArgumentException("Illegal token type " + type);
    }
}
