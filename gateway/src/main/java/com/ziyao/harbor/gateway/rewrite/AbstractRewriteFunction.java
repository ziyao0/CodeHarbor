package com.ziyao.harbor.gateway.rewrite;

import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;

/**
 * @author ziyao zhang
 * @since 2024/1/2
 */
public abstract class AbstractRewriteFunction implements RewriteFunction<byte[], byte[]> {


    /**
     * 判断是否为外部请求
     */
    public boolean isExternal() {
        return false;
    }

}
