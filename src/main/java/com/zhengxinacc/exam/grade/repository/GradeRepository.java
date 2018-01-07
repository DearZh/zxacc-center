/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月29日 下午11:48:18
 * @version 1.0
 */
public interface GradeRepository extends  MongoRepository<Grade, String>{

	/**
	 * 根据用户获取该用户所在的班级
	 * @author eko.zhan at 2018年1月7日 下午3:53:06
	 * @param users
	 * @return
	 */
	public List<Grade> findByUsersIn(List<User> users);
}
