/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zhengxinacc.exam.grade.domain.Grade;
import com.zhengxinacc.exam.paper.domain.Paper;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 上午11:07:31
 * @version 1.0
 */
public interface PaperRepository extends MongoRepository<Paper, String> {

	/**
	 * 根据班级获取该班级可参与考试的试卷
	 * @author eko.zhan at 2018年1月7日 下午3:52:49
	 * @param grades
	 * @return
	 */
	public List<Paper> findByGradesIn(List<Grade> grades);
}
