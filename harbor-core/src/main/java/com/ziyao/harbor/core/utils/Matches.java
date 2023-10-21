package com.ziyao.harbor.core.utils;

import java.util.regex.Pattern;

/**
 * @author ziyao zhang
 * @since 2023/7/20
 */
public abstract class Matches {
    private Matches() {
    }

    /**
     * 正则匹配
     *
     * @param regex 正则表达式
     * @param input 匹配内容
     * @return 返回匹配结果 <code>true</code>匹配成功
     */
    public static boolean matches(String regex, CharSequence input) {
        return Pattern.matches(regex, input);
    }

    public static boolean matches(RegexPool regex, CharSequence input) {
        return Pattern.compile(regex.getRegex()).matcher(input).matches();
    }

}
