/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.grade.repository.GradeRepository;
import com.zhengxinacc.exam.grade.service.GradeService;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.paper.domain.PaperQuestion;
import com.zhengxinacc.exam.paper.repository.PaperRepository;
import com.zhengxinacc.exam.question.domain.Question;
import com.zhengxinacc.exam.question.repository.QuestionRepository;
import com.zhengxinacc.exam.task.domain.TaskQuestion;
import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 下午8:07:09
 * @version 1.0
 */
@Service
public class PaperServiceImpl implements PaperService {

	@Resource
	private PaperRepository paperRepository;
	@Resource
	private GradeRepository gradeRepository;
	@Resource
	private GradeService gradeService;
	@Resource
	private QuestionRepository questionRepository;
	
	@Override
	public Paper save(JSONObject data) {
		String id = data.getString("id");
		Paper paper = null;
		if (StringUtils.isBlank(id)){
			paper = new Paper();
			paper.setCreateUser(data.getString("username"));
		}else{
			paper = paperRepository.findOne(id);
		}
		paper.setModifyUser(data.getString("username"));
		paper.setName(data.getString("name"));
		paper.setLimit(Integer.valueOf(data.getString("limit")));
		paper.setTotal(Integer.valueOf(data.getString("total")));
		
		JSONArray gradeArr = JSON.parseArray(data.getString("gradeIds"));
		List<Grade> gradeList = new ArrayList<Grade>();
		for (Object obj : gradeArr){
			String gradeId = (String)obj;
			Grade grade = gradeRepository.findOne(gradeId);
			if (grade!=null){
				gradeList.add(grade);
			}
		}
		paper.setGrades(gradeList);
		
		JSONArray arr = JSON.parseArray(data.getString("questions"));
		//System.out.println(arr);
		Map<String, PaperQuestion> map = new HashMap<String, PaperQuestion>();
		for (Object obj : arr){
			JSONObject json = (JSONObject) obj;
			Question ques = questionRepository.findOne(json.getString("id"));
			PaperQuestion question = new PaperQuestion(json.getString("id"), ques.getName(), ques.getType(), Integer.valueOf(json.getString("order")), Double.parseDouble(json.getString("score")));
			map.put(json.getString("id"), question);
		}
		paper.setQuestions(map);

		return paperRepository.save(paper);
	}

	@Override
	public List<Paper> findByUser(User user) {
		List<User> users = Arrays.asList(new User[]{user});
		List<Grade> gradeList = gradeRepository.findByUsersIn(users);
		
		return paperRepository.findByGradesIn(gradeList);
	}

	@Override
	public Paper setQuestionList(Paper paper) {
		Map<String, PaperQuestion> questions = paper.getQuestions();
		List<Map.Entry<String, PaperQuestion>> list = new ArrayList<Map.Entry<String, PaperQuestion>>(questions.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, PaperQuestion>>() {
			@Override
			public int compare(Map.Entry<String, PaperQuestion> o1, Map.Entry<String, PaperQuestion> o2) {
				PaperQuestion q1 = o1.getValue();
				PaperQuestion q2 = o2.getValue();
				if (q1.getOrder()!=null && q2.getOrder()!=null){
					return q1.getOrder().compareTo(q2.getOrder());
				}
				return 0;
			}
		});
		paper.setQuestionList(list);
		return paper;
	}
}
