/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.paper.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zhengxinacc.config.BaseBean;
import com.zhengxinacc.exam.grade.domain.Grade;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月30日 上午10:43:09
 * @version 1.0
 */
@Document(collection="exam_paper")
@Getter
@Setter
@ToString
public class Paper extends BaseBean {

	@Id
	private String id;
	private String name;
	private Integer limit; //试卷时长（分钟）
	private Integer total; //试卷总分
	@DBRef
	private List<Grade> grades; //考试发布的班级
	//登录的学员只能查阅在考试有效时间内的试卷
	private Date startDate; //考试有效时间-开始
	private Date endDate; //考试有效时间-结束
	private Map<String, PaperQuestion> questions; //{"score":"10","id":"5a4c6f378149763070f067cf","order":""},{"score":"10","id":"5a4c6f3e8149763070f067d0","order":""}
	@Transient
	List<Map.Entry<String, PaperQuestion>> questionList;
	
}