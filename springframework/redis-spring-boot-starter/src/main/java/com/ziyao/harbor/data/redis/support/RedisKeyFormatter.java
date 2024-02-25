package com.ziyao.harbor.data.redis.support;

import com.ziyao.harbor.core.utils.Strings;
import org.springframework.util.StringUtils;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class RedisKeyFormatter {

    static final String DELIM_STR = "{}";

    public static String format(final String keyPat, final String... args) {


        int start = 0;
        int end;
        // use string builder for better multicore performance
        StringBuilder builder = new StringBuilder(keyPat.length() + 50);

        for (String arg : args) {
            end = keyPat.indexOf(DELIM_STR, start);

            if (end == -1) {
                // no more variables
                if (start == 0) { // this is a simple string
                    return keyPat;
                } else { // add the tail string which contains no variables and return
                    // the result.
                    builder.append(keyPat, start, keyPat.length());
                    return builder.toString();
                }
            } else {
                // normal case
                builder.append(keyPat, start, end);
                builder.append(arg);
                start = end + 2;

            }
        }

        // append the characters following the last {} pair.
        builder.append(keyPat, start, keyPat.length());
        return builder.toString();
    }


    public static int countOccurrencesOf(String input) {
        return Strings.countOccurrencesOf(input, DELIM_STR);
    }

    private RedisKeyFormatter() {
    }

    public static void main(String[] args) {
        String val = "ziyao:harbor:user_center:{}:menu_tree:{}:{}";
        System.out.println(format(val, "朝丽", "2222", "xxxx"));
        System.out.println(countOccurrencesOf(val));
    }
}
