package com.ziyao.cfx.web.utils;

import com.ziyao.cfx.web.details.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class ContextUtils {

    private ContextUtils() {
    }

    public static boolean isLegal(UserDetails userDetails) {
        return !ObjectUtils.isEmpty(userDetails) && StringUtils.hasLength(userDetails.getUsername());
    }
}
