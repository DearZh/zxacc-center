/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zhengxinacc.config.BaseBean;
import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月29日 下午11:48:01
 * @version 1.0
 */
@Document(collection="exam_grade")
@Getter
@Setter
public class Grade extends BaseBean {

	@Id
	private String id;
	private String name; //班级名称
	@DBRef
	private List<User> users; //班级中的学生
}
