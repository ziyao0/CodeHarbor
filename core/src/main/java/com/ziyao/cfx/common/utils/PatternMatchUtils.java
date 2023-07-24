package com.ziyao.cfx.common.utils;

import java.util.regex.Pattern;

/**
 * @author ziyao zhang
 * @since 2023/7/20
 */
public abstract class PatternMatchUtils {
    private PatternMatchUtils() {
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

    public static boolean matches(Regex regex, CharSequence input) {
        return Pattern.compile(regex.getRegex()).matcher(input).matches();
    }


    enum Regex {
        /**
         * 手机号
         */
        MOBILE("^1[3456789]\\d{9}$"),

        /**
         * 邮箱正则
         */
        EMAIL("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),

        /**
         * URL正则
         */
        URL("^((https|http|ftp|rtsp|mms)?://)\\S+"),

        /**
         * IP4正则
         */
        IP4("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}"),

        /**
         * IP6正则
         */
        IP6("([a-f\\d]{1,4}(:[a-f\\d]{1,4}){7}|[a-f\\d]{1,4}(:[a-f\\d]{1,4}){0,7}::[a-f\\d]{0,4}(:[a-f\\d]{1,4}){0,7})"),

        /**
         * 域名
         */
        DOMAIN("[a-zA-Z\\d][-a-zA-Z\\d]{0,62}(\\.[a-zA-Z\\d][-a-zA-Z\\d]{0,62})+\\.?"),

        /**
         * 一代身份证
         */
        ID_CARD1("^[1-9]\\d{7}(?:0\\d|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}$"),

        /**
         * 二代身份证
         */
        ID_CARD2("^[1-9]\\d{5}(?:18|19|20)\\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\\d|30|31)\\d{3}[\\dXx]$"),
        ;
        private final String regex;

        Regex(String regex) {
            this.regex = regex;
        }

        public String getRegex() {
            return regex;
        }
    }
}
