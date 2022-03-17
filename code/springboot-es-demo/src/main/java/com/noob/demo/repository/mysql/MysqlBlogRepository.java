package com.noob.demo.repository.mysql;

import com.noob.demo.entity.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @description:
 * @author：holic-x
 * @date: 2022/3/16
 * @Copyright： 无所事事是薄弱意志的避难所
 */
public interface MysqlBlogRepository extends JpaRepository<MysqlBlog,Integer> {

    @Query("select e from MysqlBlog e order by e.createTime desc")
    List<MysqlBlog> queryAll();

    @Query("select e from MysqlBlog e where e.title like concat('%',:keyword,'%') or e.content like concat('%',:keyword,'%') order by e.createTime desc")
    List<MysqlBlog> queryBlogs(@Param("keyword")String keyword);
}
