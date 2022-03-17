package com.noob.demo.controller;

import com.noob.demo.entity.mysql.MysqlBlog;
import com.noob.demo.repository.es.EsBlogRepository;
import com.noob.demo.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author：holic-x
 * @date: 2022/3/16
 * @Copyright： 无所事事是薄弱意志的避难所
 */
@RestController
public class DataController {

    @Autowired
    MysqlBlogRepository msqlBlogRepository;
    @Autowired
    EsBlogRepository esBlogRepository;

    @RequestMapping("/blogs")
    public Object blogs() {
        List<MysqlBlog> list = msqlBlogRepository.queryAll();
        return list;
    }

    @RequestMapping("/search")
    public Object search(@RequestBody Param param)throws Exception {
        // 定义返回对象
        HashMap<String, Object> map = new HashMap<>();
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        String keyword = param.getKeyword();
        if (type.equalsIgnoreCase("mysql")) {
            // mysql查询
            List<MysqlBlog> mysqlBlogList = msqlBlogRepository.queryBlogs(keyword);
            map.put("list", mysqlBlogList);
        } else if (type.equalsIgnoreCase("es")) {
            // 构建查询参数
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title",keyword));
            builder.should(QueryBuilders.matchPhraseQuery("content",keyword));
            String queryStr = builder.toString();
            System.out.println(queryStr);
//            Page<EsBlog> search =(Page<EsBlog>) esBlogRepository.search(queryStr);
//            List<EsBlog> list = search.getContent();

            ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                    .connectedTo("localhost:9200")
                    .build();

            RestHighLevelClient restHighLevelClient = RestClients.create(clientConfiguration).rest();

            //创建搜索对象
            SearchRequest searchRequest = new SearchRequest("blog");
            //搜索构建对象
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(builder);
//            searchSourceBuilder.query(QueryBuilders.matchAllQuery())//执行查询条件
//                    .from(0)//起始条数
//                    .size(10)//每页展示记录
//                    .postFilter(QueryBuilders.matchAllQuery());//过滤条件
//                    .sort("createTime", SortOrder.DESC);//排序

            //创建搜索请求
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("符合条件的文档总数: " + searchResponse.getHits().getTotalHits());
            System.out.println("符合条件的文档最大得分: " + searchResponse.getHits().getMaxScore());
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit : hits) {
                System.out.println(hit.getSourceAsMap());
            }
            map.put("list", hits);
        } else {
            return "error type";
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        map.put("duration", totalTimeMillis);
        return map;
    }

    @RequestMapping("/blog/{id}")
    public Object blog(@PathVariable("id")Integer id) {
        Optional<MysqlBlog> blog = msqlBlogRepository.findById(id);
        return blog.get();
    }

    /**
     * 构建请求参数
     */
    @Data
    public static class Param {
        // mysql,es
        private String type;
        private String keyword;
    }
}
