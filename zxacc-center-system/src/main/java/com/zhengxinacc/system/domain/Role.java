/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zhengxinacc.common.config.BaseBean;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 上午11:04:14
 * @version 1.0
 */
@Document(collection="sys_role")
@Getter
@Setter
public class Role extends BaseBean {

	@Id
	private String id;
	private String name;
	private String key;
	@DBRef
	private List<Permission> permissions;
	@DBRef
	private List<User> users;
	
}
