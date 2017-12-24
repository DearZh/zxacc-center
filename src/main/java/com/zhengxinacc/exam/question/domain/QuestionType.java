/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.domain;
/**
 * 题目类型
 * 0-单选题
 * 1-多选题
 * 2-判断题
 * 3-问答题
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 下午1:06:32
 * @version 1.0
 */
public enum QuestionType {

	Single(0), Multi(1), Tof(2);
	
	private Integer code;
	
	private QuestionType(Integer code){
		this.code = code;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.code);
	}
}
