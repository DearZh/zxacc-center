/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.article.repository;

import com.zhengxinacc.article.domain.Article;
import com.zhengxinacc.system.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan>/a>
 * @version 1.0
 * @since 2018-10-28 13:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTests {

    @Resource
    private ArticleRepository articleRepository;

    @Test
    public void testFindAll(){
        Iterable<Article> it = articleRepository.findAll();
        it.forEach(article -> {
            System.out.println(article.getTitle());
        });
    }
}
