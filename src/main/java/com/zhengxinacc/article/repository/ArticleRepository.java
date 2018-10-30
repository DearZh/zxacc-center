/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.article.repository;

import com.zhengxinacc.article.domain.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan>/a>
 * @version 1.0
 * @since 2018-10-28 13:06
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {

}
