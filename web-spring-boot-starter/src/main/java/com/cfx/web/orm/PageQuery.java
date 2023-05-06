package com.cfx.web.orm;

/**
 * @author Eason
 * @since 2023/5/6
 */
public class PageQuery<T> {

    private T query;

    private PageI page = new PageI();


    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }

    public PageI getPage() {
        return page;
    }

    public void setPage(PageI page) {
        this.page = page;
    }

    public static class PageI {

        /**
         * 当前页条数
         */
        private long size = 10;

        /**
         * 当前页
         */
        private long current = 1;

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public long getCurrent() {
            return current;
        }

        public void setCurrent(long current) {
            this.current = current;
        }
    }
}
