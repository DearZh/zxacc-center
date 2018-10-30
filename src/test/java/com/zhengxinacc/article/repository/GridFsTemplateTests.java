/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.article.repository;

import com.mongodb.gridfs.GridFSDBFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan>/a>
 * @version 1.0
 * @since 2018-10-29 22:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GridFsTemplateTests {

    @Resource
    private GridFsTemplate gridFsTemplate;

    @Test
    public void testFindOne(){
        Criteria criteria = GridFsCriteria.where("filename").is("1528429921");
        Query query = new Query(criteria);
        GridFSDBFile imgFile = gridFsTemplate.findOne(query);
        System.out.println(imgFile);
    }

    @Test
    public void testFindById(){
        Criteria criteria = GridFsCriteria.whereFilename().is(1528429921);
        Query query = new Query(criteria);
        GridFSDBFile imgFile = gridFsTemplate.findOne(query);
        System.out.println(imgFile);
    }
}
