/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
	//TODO 为什么 @Query 获取的返回数据为空？
	@Query("{'delFlag': 0, 'grades': {'$in': ?0}}") //or @Query("{'delFlag': 0, 'grades': {'$in': [?0]}}")
	public List<Paper> findByGradesIn(List<Grade> grades);

	public Page<Paper> findByNameLike(String keyword, Pageable pageable);
	
	/**
	 * 获取已过期的试卷，定时修改该试卷的状态
	 * @author eko.zhan at 2018年5月6日 下午5:53:49
	 * @param date
	 * @return
	 */
	@Query("{'delFlag': 0, '$and': [{'endDate': {'$ne': null}}, {'endDate': {'$lt': ?0}}]}")
	public List<Paper> findByEndDateLessThan(Date date);
}
