/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.zhengxinacc.exam.question.domain.Question;
import com.zhengxinacc.exam.question.repository.QuestionRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 下午4:33:33
 * @version 1.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

	@Resource
	private QuestionRepository questionRepository;
	
	@Override
	public Page<Question> findAll(Integer page, Integer size, String property, Direction desc) {
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		return questionRepository.findAll(pageable);
	}

}
