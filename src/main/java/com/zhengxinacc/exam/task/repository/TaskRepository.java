/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.task.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zhengxinacc.exam.paper.domain.Paper;
import com.zhengxinacc.exam.task.domain.Task;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月3日 下午9:09:01
 * @version 1.0
 */
public interface TaskRepository extends MongoRepository<Task, String> {

	/**
	 * 根据试卷获取所有考试任务
	 * @author eko.zhan
	 * @param paper
	 * @return
	 */
	public List<Task> findByPaper(Paper paper);
	/**
	 * 根据试卷和用户获取考试任务
	 * @author eko.zhan
	 * @param paper
	 * @param username
	 * @return
	 */
	public Task findByPaperAndCreateUser(Paper paper, String username);
	/**
	 * 获取指定用户的考试任务
	 * @author eko.zhan at 2018年5月5日 下午2:28:22
	 * @param username
	 * @return
	 */
	public List<Task> findByCreateUser(String username);
}
