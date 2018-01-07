/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.system.user.domain.User;

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
}
