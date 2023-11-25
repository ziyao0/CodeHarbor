package com.ziyao.harbor.elasticsearch.test.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author ziyao zhang
 * @since 2023/11/17
 */
@Document(indexName = "audit_log_index")
public class AuditLog {

    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long level;
    @Field(type = FieldType.Text)
    private String log;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
