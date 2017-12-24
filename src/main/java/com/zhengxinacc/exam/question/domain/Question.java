/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.question.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengxinacc.config.BaseBean;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午10:03:11
 * @version 1.0
 */
@Document(collection="exam_question")
@Getter
@Setter
public class Question extends BaseBean {

	@Id
	private String id;
	private String name;
	@JsonIgnore
	@JSONField(serialize=false)
	@DBRef
	private QuestionCate cate;
	@DBRef
	private List<Answer> answers;
	//记录正确答案
	private String[] rightKeys;
	//题目类型
	private Integer type;
	
}
