/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.domain.Answer;
import com.zhengxinacc.exam.domain.Question;
import com.zhengxinacc.exam.domain.QuestionCate;
import com.zhengxinacc.exam.repository.AnswerRepository;
import com.zhengxinacc.exam.repository.QuestionCateRepository;
import com.zhengxinacc.exam.repository.QuestionRepository;
import com.zhengxinacc.exam.service.QuestionService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 下午4:33:33
 * @version 1.0
 */
@Service
public class QuestionServiceImpl implements QuestionService {

	@Resource
	private QuestionRepository questionRepository;
	@Resource
	private AnswerRepository answerRepository;
	@Resource
	private QuestionCateRepository questionCateRepository;
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public Page<Question> findAll(Integer page, Integer size, JSONObject data, Direction desc) {
		String property = data.getString("property");
		String cateId = data.getString("cateId");
		String keyword = data.getString("keyword");
		Integer type = data.getInteger("type");
		
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		if (StringUtils.isBlank(cateId)){
			//无分类
			if (StringUtils.isBlank(keyword)){
				if (type==null){
					return questionRepository.findAll(pageable);
				}else{
					return questionRepository.findByType(type, pageable);
				}
			}else{
				if (type==null){
					return questionRepository.findByNameLike(keyword, pageable);
				}else{
					return questionRepository.findByNameLikeAndType(keyword, type, pageable);
				}
			}
		}else{
			//有分类
			//TODO spring mongo db 关联查询
			QuestionCate questionCate = questionCateRepository.findOne(cateId);
//			ExampleMatcher matcher = ExampleMatcher.matching()
//					.withMatcher("cateId", GenericPropertyMatchers.contains())
//					.withIgnoreNullValues()
//					.withIgnorePaths(new String[]{"id", "name", "cate", "answers", "key", "type"});
//			Question question = new Question();
//			question.setCateId(cateId);
//			Example<Question> example = Example.of(question, matcher);
			
//			return questionRepository.findAll(example, pageable);
			
			//mongoTemplate 的方法
//			Query query = new Query(Criteria.where("cate").in(new QuestionCate[]{questionCate}));
//			long total = mongoTemplate.count(query, Question.class);
//			query.limit(size);
//			query.skip((page-1)*size);
//			List<Question> list = mongoTemplate.find(query, Question.class);
//			Page<Question> pager = new PageImpl<Question>(list, pageable, total);
			
			if (StringUtils.isBlank(keyword)){
				if (type==null){
					return questionRepository.findByCate(questionCate, pageable);
				}else{
					return questionRepository.findByCateAndType(questionCate, type, pageable);
				}
			}else{
				if (type==null){
					return questionRepository.findByCateAndNameLike(questionCate, keyword, pageable);
				}else{
					return questionRepository.findByCateAndNameLikeAndType(questionCate, keyword, type, pageable);
				}
				
			}
		}
	}

	@Transactional
	@Override
	public void delete(String id) {
		Question question = questionRepository.findOne(id);
		if (question.getAnswers()!=null){
			answerRepository.delete(question.getAnswers());
		}
		questionRepository.delete(question);
	}

	@Transactional
	@Override
	public Question save(JSONObject data, String username) {
		String id = data.getString("id");
		String name = data.getString("name");
		String cateid = data.getString("cateid");
		String type = data.getString("type");
		String answers = data.getString("answers");
		String key = data.getString("key");

		List<String> list = JSON.parseArray(answers, String.class);
		Question question = null;
		if (StringUtils.isBlank(id)){
			question = new Question();
			question.setCreateUser(username);
		}else{
			question = questionRepository.findOne(id);
		}
		question.setModifyUser(username);
		question.setName(name);
		question.setCate(questionCateRepository.findOne(cateid));
		question.setType(Integer.valueOf(type));
		if (StringUtils.isNotBlank(key)){
			question.setKey(Boolean.valueOf(key));
		}
		question = questionRepository.save(question);
		
		List<Answer> answerList = new ArrayList<Answer>();
		if (list.size()>0){
			for (String answer : list){
				JSONObject json = JSON.parseObject(answer);
				Answer answerBean = null;
				if (StringUtils.isBlank(json.getString("id"))){
					answerBean = new Answer();
				}else{
					answerBean = answerRepository.findOne(json.getString("id"));
				}
				answerBean.setName(json.getString("content"));
				answerBean.setKey(json.getBoolean("key"));
				answerBean.setQuestion(question);
				answerRepository.save(answerBean);
				answerList.add(answerBean);
			}
			question.setAnswers(answerList);
		}
		
		return questionRepository.save(question);
	}

}
