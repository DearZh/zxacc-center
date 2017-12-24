/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.zhengxinacc.exam.question.domain.Question;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 下午4:32:31
 * @version 1.0
 */
public interface QuestionService {

	public Page<Question> findAll(Integer page, Integer size, String property, Direction desc);
}
