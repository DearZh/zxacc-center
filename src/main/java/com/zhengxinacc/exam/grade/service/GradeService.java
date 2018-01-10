/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.grade.domain.Grade;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 上午10:05:23
 * @version 1.0
 */
public interface GradeService {

	/**
	 * 保存班级
	 * @author eko.zhan at 2017年12月30日 上午10:23:20
	 * @param data
	 * @return
	 */
	public Grade save(JSONObject data);

	/**
	 * 删除班级
	 * @author eko.zhan at 2017年12月30日 上午10:23:27
	 * @param id
	 */
	public void delete(String id);
	/**
	 * 获取班级集合
	 * @author eko.zhan at 2018年1月10日 上午10:07:35
	 * @param page
	 * @param size
	 * @param data
	 * @param desc
	 * @return
	 */
	public Page<Grade> findAll(Integer page, Integer size, JSONObject data, Direction desc);
}
