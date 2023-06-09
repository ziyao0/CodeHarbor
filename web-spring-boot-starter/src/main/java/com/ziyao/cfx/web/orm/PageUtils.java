package com.ziyao.cfx.web.orm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ziyao zhang
 * @since 2023/5/6
 */
public abstract class PageUtils {


    public static <T> Page<T> initPage(PageQuery<?> pageQuery, Class<T> type) {

        return new Page<>(pageQuery.getPage().getCurrent(), pageQuery.getPage().getSize());
    }
}
