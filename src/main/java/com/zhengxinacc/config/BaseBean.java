/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.config;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 上午11:25:35
 * @version 1.0
 */
@Getter
@Setter
public class BaseBean implements Serializable {

	private String createUser;
	private Date createDate = new Date();
	private String modifyUser;
	private Date modifyDate = new Date();
	private Integer delFlag = 0;
}
