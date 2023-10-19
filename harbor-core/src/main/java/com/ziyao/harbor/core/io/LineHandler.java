package com.ziyao.harbor.core.io;

/**
 * @author ziyao zhang
 * @since 2023/10/19
 */
@FunctionalInterface
public interface LineHandler {
    /**
     * 处理一行数据，可以编辑后存入指定地方
     * @param line 行
     */
    void handle(String line);
}
