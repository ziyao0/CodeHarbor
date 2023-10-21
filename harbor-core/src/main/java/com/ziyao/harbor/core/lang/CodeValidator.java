package com.ziyao.harbor.core.lang;

import java.util.regex.Pattern;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class CodeValidator {

    /**
     * 验证是否为Hex（16进制）字符串
     *
     * @param value 值
     * @return 是否为Hex（16进制）字符串
     */
    public static boolean isHex(CharSequence value) {
        return isMatchRegex(Pattern.compile("^[a-fA-F0-9]+$"), value);
    }

    /**
     * 通过正则表达式验证
     *
     * @param pattern 正则模式
     * @param value   值
     * @return 是否匹配正则
     */
    public static boolean isMatchRegex(Pattern pattern, CharSequence value) {
        return isMatch(pattern, value);
    }


    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param content 内容
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(Pattern pattern, CharSequence content) {
        if (content == null || pattern == null) {
            // 提供null的字符串为不匹配
            return false;
        }
        return pattern.matcher(content).matches();
    }
}
