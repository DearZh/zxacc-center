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

	public List<Task> findByPaper(Paper paper);
	public Task findByPaperAndCreateUser(Paper paper, String username);
	public List<Task> findByCreateUser(String username);
}
