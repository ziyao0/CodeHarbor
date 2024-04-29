package com.ziyao.harbor.core.text;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class StringFinder extends TextFinder {

    @Serial
    private static final long serialVersionUID = -3893478424730695351L;
    private final CharSequence strToFind;
    private final boolean caseInsensitive;

    /**
     * 构造
     *
     * @param strToFind       被查找的字符串
     * @param caseInsensitive 是否忽略大小写
     */
    public StringFinder(CharSequence strToFind, boolean caseInsensitive) {
        Assert.notNull(strToFind);
        this.strToFind = strToFind;
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public int start(int from) {
        Assert.notNull(this.text, "Text to find must be not null!");
        final int subLen = strToFind.length();

        if (from < 0) {
            from = 0;
        }
        int endLimit = getValidEndIndex();
        if (negative) {
            for (int i = from; i > endLimit; i--) {
                if (Strings.isSubEquals(text, i, strToFind, 0, subLen, caseInsensitive)) {
                    return i;
                }
            }
        } else {
            endLimit = endLimit - subLen + 1;
            for (int i = from; i < endLimit; i++) {
                if (Strings.isSubEquals(text, i, strToFind, 0, subLen, caseInsensitive)) {
                    return i;
                }
            }
        }

        return INDEX_NOT_FOUND;
    }

    @Override
    public int end(int start) {
        if (start < 0) {
            return -1;
        }
        return start + strToFind.length();
    }
}
