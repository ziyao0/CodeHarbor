package com.ziyao.harbor.web.base;

import lombok.Getter;

/**
 * @author ziyao zhang
 * @since 2023/5/6
 */
@Getter
public class PageParams<T> {

    private T params;

    private PageI page = new PageI();


    public void setParams(T params) {
        this.params = params;
    }

    public void setPage(PageI page) {
        this.page = page;
    }

    @Getter
    public static class PageI {

        /**
         * 当前页条数
         */
        private long size = 10;

        /**
         * 当前页
         */
        private long current = 1;

        public void setSize(long size) {
            this.size = size;
        }

        public void setCurrent(long current) {
            this.current = current;
        }
    }
}
