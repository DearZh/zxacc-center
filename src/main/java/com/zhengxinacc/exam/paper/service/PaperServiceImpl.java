/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.zhengxinacc.exam.paper.repository.PaperRepository;
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
		paper.setQuestions(arr);

		return paperRepository.save(paper);
	}

	@Override
	public List<Paper> findByUser(User user) {
		List<Grade> gradeList = gradeService.findByUser(user);
		return findByGrade(gradeList);
	}

	@Override
	public List<Paper> findByGrade(List<Grade> gradeList) {
		ExampleMatcher matcher = ExampleMatcher.matchingAny();
		Paper paper = new Paper();
		paper.setGrades(gradeList);
		Example<Paper> example = Example.of(paper, matcher);
		List<Paper> list = paperRepository.findAll(example);
		return list;
	}

}
