/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.task.service;

import com.zhengxinacc.exam.task.domain.Task;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月3日 下午9:08:29
 * @version 1.0
 */
public interface TaskService {

	/**
	 * 保存答题卡，临时保存
	 * @author eko.zhan at 2018年1月6日 上午11:32:54
	 * @param paperId
	 * @param quesId
	 * @param ans
	 */
	public Task saveQuestion(String paperId, String quesId, String ans, Integer limit, String username);
	/**
	 * 根据试卷初始化答卷
	 * @author eko.zhan at 2018年1月6日 下午12:08:22
	 * @param paperId
	 * @return
	 */
	public Task init(String paperId, String username);
	/**
	 * 答卷完毕
	 * @author eko.zhan at 2018年1月6日 下午2:01:42
	 * @param paperId 试卷id
	 * @param username
	 * @return
	 */
	public Task submit(String paperId, String username);
	/**
	 * 处理试题排序
	 * @author eko.zhan at 2018年1月8日 下午4:27:50
	 * @param task
	 * @return
	 */
	public Task setQuestionList(Task task);
}
