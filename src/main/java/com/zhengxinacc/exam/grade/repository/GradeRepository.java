/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zhengxinacc.exam.grade.domain.Grade;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月29日 下午11:48:18
 * @version 1.0
 */
public interface GradeRepository extends  MongoRepository<Grade, String>{

}
