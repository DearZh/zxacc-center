/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.domain.Question;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 下午4:32:31
 * @version 1.0
 */
public interface QuestionService {

	/**
	 * 获取所有问题列表
	 * @author eko.zhan at 2017年12月24日 下午9:02:19
	 * @param page
	 * @param size
	 * @param property
	 * @param desc
	 * @return
	 */
	public Page<Question> findAll(Integer page, Integer size, JSONObject data, Direction desc);
	/**
	 * 删除问题，同时删除答案
	 * @author eko.zhan at 2017年12月24日 下午9:02:44
	 * @param id
	 */
	public void delete(String id);
	/**
	 * 保存问题以及问题上的答案
	 * @author eko.zhan at 2017年12月29日 下午10:14:58
	 * @param data
	 * @return
	 */
	public Question save(JSONObject data, String username);
}
