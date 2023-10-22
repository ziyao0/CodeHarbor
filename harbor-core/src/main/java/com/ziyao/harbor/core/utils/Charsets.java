package com.ziyao.harbor.core.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
public abstract class Charsets {
    /**
     * GBK
     */
    public static final String GBK = "GBK";
    public static final String UTF_8 = "UTF-8";
    public static final String ISO_8859_1 = "ISO-8859-1";
    /**
     * ISO-8859-1
     */
    public static final Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    public static Charset CHARSET_GBK = Charset.forName(GBK);


    public static Charset forName(String charsetName) {
        return Strings.hasLength(charsetName) ? Charset.forName(charsetName) : Charset.defaultCharset();
    }

    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }
}
