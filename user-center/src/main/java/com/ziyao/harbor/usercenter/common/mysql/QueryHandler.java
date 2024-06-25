package com.ziyao.harbor.usercenter.common.mysql;

import java.sql.SQLException;

/**
 * @author ziyao zhang
 * @since 2023/9/26
 */
public interface QueryHandler {

    <T> T process(Class<T> clazz,
                  Object... args) throws SQLException;
}
