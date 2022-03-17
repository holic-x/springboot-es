package com.noob.demo.entity.mysql;

import lombok.Data;

import javax.persistence.*;

/**
 * @description:
 * @author：holic-x
 * @date: 2022/3/16
 * @Copyright： 无所事事是薄弱意志的避难所
 */
@Entity
@Data
@Table(name="t_blog")
public class MysqlBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键id生成策略(此处设定为mysql的自增长id)
    private Integer id;

    private String title;
    private String author;
    @Column(columnDefinition = "mediumtext") // 定义content对应指定mediumtext列
    private String content;
    // 驼峰jpa自动转化
    private String createTime;
    private String updateTime;

}
