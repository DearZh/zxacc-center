/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.domain;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 上午11:57:10
 * @version 1.0
 */
@Document(collection="sys_menu")
@Getter
@Setter
public class Menu {
	
	@Id
	private String id;
	private String name;
	private String key;
	private String url;
	
}
