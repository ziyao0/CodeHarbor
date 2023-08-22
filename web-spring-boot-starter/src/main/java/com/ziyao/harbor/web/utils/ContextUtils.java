package com.ziyao.harbor.web.utils;

import com.ziyao.harbor.common.utils.Strings;
import com.ziyao.harbor.web.details.UserDetails;

import java.util.Objects;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
public abstract class ContextUtils {

    private ContextUtils() {
    }

    public static boolean isLegal(UserDetails userDetails) {
        return Objects.nonNull(userDetails) && Strings.hasLength(userDetails.getUsername());
    }
}
