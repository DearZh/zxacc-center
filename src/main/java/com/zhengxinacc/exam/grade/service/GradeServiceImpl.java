/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.grade.repository.GradeRepository;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.repository.UserRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 上午10:06:00
 * @version 1.0
 */
@Service
public class GradeServiceImpl implements GradeService {

	@Resource
	private GradeRepository gradeRepository;
	@Resource
	private UserRepository userRepository;
	
	@Override
	public Grade save(JSONObject data) {
		String id = data.getString("id");
		String username = data.getString("username");
		String name = data.getString("name");
		
		Grade grade = null;
		if (StringUtils.isNotBlank(id)){
			grade = gradeRepository.findOne(id);
		}else{
			grade = new Grade();
			grade.setCreateUser(username);
		}
		grade.setModifyDate(new Date());
		grade.setModifyUser(username);
		grade.setName(name);
		
		List<User> userList = new ArrayList<User>();
		JSONArray arr = data.getJSONArray("users");
		if (arr!=null){
			for (Object obj : arr){
				String userid = (String) obj;
				User user = userRepository.findOne(userid);
				if (user!=null){
					userList.add(user);
				}
			}
		}
		grade.setUsers(userList);
		gradeRepository.save(grade);
		
		
		return grade;
	}

	@Override
	public void delete(String id) {
		gradeRepository.delete(id);
	}

	@Override
	public Page<Grade> findAll(Integer page, Integer size, JSONObject data, Direction desc) {
		String property = data.getString("property");
		String keyword = data.getString("keyword");
		
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		
		if (StringUtils.isBlank(keyword)){
			return gradeRepository.findAll(pageable);
		}else{
			return gradeRepository.findByNameLike(keyword, pageable);
		}
	}

}
