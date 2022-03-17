package com.noob.demo;

import com.noob.demo.entity.es.EsBlog;
import com.noob.demo.repository.es.EsBlogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;

@SpringBootTest
class SpringbootEsDemoApplicationTests {

    @Test
    void contextLoads() {
    }



    @Autowired
    private EsBlogRepository esBlogRepository;

    @Test
    public void testEsBlog(){
        Iterable<EsBlog> all = esBlogRepository.findAll();
        Iterator<EsBlog> iterator = all.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
