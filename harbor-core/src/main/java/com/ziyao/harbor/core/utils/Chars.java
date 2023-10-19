package com.ziyao.harbor.core.utils;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class Chars {
    /**
     * 字符常量：空格符 {@code ' '}
     */
    public static final char SPACE = ' ';
    /**
     * 字符常量：制表符 {@code '\t'}
     */
    public static final char TAB = '	';
    /**
     * 字符常量：点 {@code '.'}
     */
    public static final char DOT = '.';
    /**
     * 字符常量：斜杠 {@code '/'}
     */
    public static final char SLASH = '/';
    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    public static final char BACKSLASH = '\\';
    /**
     * 字符常量：回车符 {@code '\r'}
     */
    public static final char CR = '\r';
    /**
     * 字符常量：换行符 {@code '\n'}
     */
    public static final char LF = '\n';
    /**
     * 字符常量：减号（连接符） {@code '-'}
     */
    public static final char DASHED = '-';
    /**
     * 字符常量：下划线 {@code '_'}
     */
    public static final char UNDERLINE = '_';
    /**
     * 字符常量：逗号 {@code ','}
     */
    public static final char COMMA = ',';
    /**
     * 字符常量：花括号（左） <code>'{'</code>
     */
    public static final char DELIM_START = '{';
    /**
     * 字符常量：花括号（右） <code>'}'</code>
     */
    public static final char DELIM_END = '}';
    /**
     * 字符常量：中括号（左） {@code '['}
     */
    public static final char BRACKET_START = '[';
    /**
     * 字符常量：中括号（右） {@code ']'}
     */
    public static final char BRACKET_END = ']';
    /**
     * 字符常量：双引号 {@code '"'}
     */
    public static final char DOUBLE_QUOTES = '"';
    /**
     * 字符常量：单引号 {@code '\''}
     */
    public static final char SINGLE_QUOTE = '\'';
    /**
     * 字符常量：与 {@code '&'}
     */
    public static final char AMP = '&';
    /**
     * 字符常量：冒号 {@code ':'}
     */
    public static final char COLON = ':';
    /**
     * 字符常量：艾特 {@code '@'}
     */
    public static final char AT = '@';

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符<br>
     * 空白符包括空格、制表符、全角空格和不间断空格<br>
     *
     * @param c 字符
     * @return 是否空白符
     * @see Character#isWhitespace(int)
     * @see Character#isSpaceChar(int)
     * @since 4.0.10
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c)
                || Character.isSpaceChar(c)
                || c == '\ufeff'
                || c == '\u202a'
                || c == '\u0000'
                // issue#I5UGSQ，Hangul Filler
                || c == '\u3164'
                // Braille Pattern Blank
                || c == '\u2800'
                // MONGOLIAN VOWEL SEPARATOR
                || c == '\u180e';
    }
}
