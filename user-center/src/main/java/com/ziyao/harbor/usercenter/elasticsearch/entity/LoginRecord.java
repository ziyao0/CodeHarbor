package com.ziyao.harbor.usercenter.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * @author ziyao zhang
 * @since 2023/12/25
 */
@Data
@Builder
@Setting
@Document(indexName = "login_record")
public class LoginRecord {

    @Id
    private Long userId;

    @Field(type = FieldType.Long)
    private Long appid;

    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Keyword)
    private String nickname;
    /**
     * 登录时间
     */
    @Field(type = FieldType.Date)
    private Date loginTime;

    @Field(type = FieldType.Ip)
    private String ip;

    @Field(type = FieldType.Long)
    private Long deptId;

    @Field(type = FieldType.Keyword)
    private String deptName;

    @Field(type = FieldType.Keyword)
    public String loginStatus;
}
