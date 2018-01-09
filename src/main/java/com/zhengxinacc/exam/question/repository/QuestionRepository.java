/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zhengxinacc.exam.question.domain.Question;
import com.zhengxinacc.exam.question.domain.QuestionCate;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午10:08:45
 * @version 1.0
 */
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

	
	Page<Question> findByNameLike(String name, Pageable pageable);
	
	Page<Question> findByNameLikeAndType(String name, Integer type, Pageable pageable);
	
	Page<Question> findByCate(QuestionCate cate, Pageable pageable);
	
	Page<Question> findByCateAndNameLike(QuestionCate cate, String name, Pageable pageable);

	Page<Question> findByType(Integer type, Pageable pageable);

	Page<Question> findByCateAndType(QuestionCate questionCate, Integer type,
			Pageable pageable);

	Page<Question> findByCateAndNameLikeAndType(QuestionCate questionCate,
			String keyword, Integer type, Pageable pageable);
	

}
