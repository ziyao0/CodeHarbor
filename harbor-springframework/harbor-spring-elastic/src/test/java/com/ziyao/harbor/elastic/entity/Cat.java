package com.ziyao.harbor.elastic.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author ziyao zhang
 * @since 2023/11/14
 */
@Document(indexName = "cat")
public class Cat {

    @Id
    private long id;
    @Field(type = FieldType.Auto)
    private String name;
    @Field(type = FieldType.Auto)
    private String age;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String characteristic;

    public Cat() {
    }

    public Cat(long id, String name, String age, String characteristic) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.characteristic = characteristic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }
}
