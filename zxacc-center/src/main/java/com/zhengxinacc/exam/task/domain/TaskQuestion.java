/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.task.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.zhengxinacc.exam.question.domain.Answer;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月3日 下午9:39:27
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskQuestion {

	private String id;	//对应问题主键id
	private String name;
	private Integer type;
	private Integer order = 0;
	private Double score;
	private Boolean key; //记录判断题正确答案
	private Boolean keyMark = Boolean.FALSE; //记录判断题答题答案 
	private Boolean isReply = Boolean.TRUE; //标识当前是答卷中的问题
	private List<Answer> answers;	//根据是否有  question 对象，判断是否答题
}
