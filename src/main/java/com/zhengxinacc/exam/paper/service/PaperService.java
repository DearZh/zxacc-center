/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 下午8:02:17
 * @version 1.0
 */
public interface PaperService {

	/**
	 * 保存试卷
	 * @author eko.zhan at 2017年12月30日 下午8:06:43
	 * @param data
	 * @return
	 */
	public Paper save(JSONObject data);
	/**
	 * 根据用户查找当前用户可以参加的考试试卷
	 * @author eko.zhan at 2018年1月1日 下午4:00:41
	 * @param user
	 * @return
	 */
	public List<Paper> findByUser(User user);
}
