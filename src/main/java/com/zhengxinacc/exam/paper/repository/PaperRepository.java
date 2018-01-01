/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zhengxinacc.exam.paper.domain.Paper;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 上午11:07:31
 * @version 1.0
 */
public interface PaperRepository extends MongoRepository<Paper, String> {

}
