package com.ziyao.harbor.usercenter.common.mysql;

import com.ziyao.harbor.core.utils.Strings;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/**
 * @author ziyao zhang
 * @since 2023/9/26
 */
public class QueryProcessor implements QueryHandler {

    private final JdbcTemplate jdbcTemplate;

    private final String query;

    public QueryProcessor(JdbcTemplate jdbcTemplate, String query) {
        this.jdbcTemplate = jdbcTemplate;
        this.query = query;
    }

    @Override
    public <T> T process(Class<T> clazz, Object... args) throws SQLException {
        try {
            if (Strings.isEmpty(query)) {
                throw new SQLException("The SQL statement is not initialized");
            }
            return processStatement(query, clazz, args);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private <T> T processStatement(String query, Class<T> clazz, Object... args) {
        // 调用数据库进行查询处理
        return jdbcTemplate.queryForObject(query, clazz, args);
    }
}
