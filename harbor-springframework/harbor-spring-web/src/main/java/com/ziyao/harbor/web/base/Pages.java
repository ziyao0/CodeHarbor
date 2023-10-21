package com.ziyao.harbor.web.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ziyao zhang
 * @since 2023/5/6
 */
public abstract class Pages {


    public static <T> Page<T> initPage(PageParams<?> pageParams, Class<T> type) {

        return new Page<>(pageParams.getPage().getCurrent(), pageParams.getPage().getSize());
    }
}
