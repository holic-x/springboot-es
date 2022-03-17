package com.noob.demo.entity.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

/**
 * @description:
 * @author：holic-x
 * @date: 2022/3/16
 * @Copyright： 无所事事是薄弱意志的避难所
 */
@Data
@Document(indexName = "blog", useServerConfiguration = true, createIndex = false)
public class EsBlog {

    @Id
    private Integer id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String author;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private String createTime;

    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private String updateTime;

}