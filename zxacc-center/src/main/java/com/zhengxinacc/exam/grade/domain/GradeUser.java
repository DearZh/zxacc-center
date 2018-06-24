/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.grade.domain;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zhengxinacc.common.config.BaseBean;
import com.zhengxinacc.system.user.domain.User;

/**
 * 用于记录班级学生关系，从班级查找学生直接使用 grade.getUsers，如果要查学生所在的班级，使用这张表
 * 学员与班级是多对多关系
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月29日 下午11:48:01
 * @version 1.0
 */
@Document(collection="exam_grade_user")
@Getter
@Setter
@Deprecated //暂时不用这张表，可能没有必要知道用户在哪个班级里，班级的概念是考试发布范围，并不是学校里的那种班级
public class GradeUser extends BaseBean {

	@Id
	private String id;
	@DBRef
	private User user;
	@DBRef
	private Grade grade;
}
