package com.ziyao.harbor.core.token;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * token类型
 *
 * @author zhangziyao
 * @date 2023/4/23
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
    ;

    public final String type;

    TokenType(String type) {
        this.type = type;
    }

    private static final List<TokenType> list;

    static {
        list = Arrays.stream(values()).toList();
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
                return token.replace(tokenType.getType(), "");
            }
        }
        return null;
    }
}
