/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zhengxinacc.exam.question.domain.Answer;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午10:08:45
 * @version 1.0
 */
@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {

}
