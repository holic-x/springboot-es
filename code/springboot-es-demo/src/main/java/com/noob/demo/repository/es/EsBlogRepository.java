package com.noob.demo.repository.es;

import com.noob.demo.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @description:
 * @author：holic-x
 * @date: 2022/3/17
 * @Copyright： 无所事事是薄弱意志的避难所
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,Integer> {
}
