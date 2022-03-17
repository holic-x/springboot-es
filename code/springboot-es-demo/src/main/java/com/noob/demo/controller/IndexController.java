package com.noob.demo.controller;

import com.noob.demo.entity.mysql.MysqlBlog;
import com.noob.demo.repository.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @description:
 * @author：holic-x
 * @date: 2022/3/16
 * @Copyright： 无所事事是薄弱意志的避难所
 */
@Controller
public class IndexController {

    @Autowired
    private MysqlBlogRepository msqlBlogRepository;

    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> list = msqlBlogRepository.findAll();
        System.out.println(list.size());
        return "index.html";
    }

}
