package com.ziyao.harbor.usercenter.mysql;

/**
 * @author ziyao zhang
 * @since 2023/9/26
 */
public interface SQLStatement {


    void validate(Object o);

    <T> T execute();
}
