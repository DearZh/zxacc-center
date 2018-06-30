/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 上午11:05:12
 * @version 1.0
 */
@Getter
@Setter
public class UserInfo {
	
	private String username;
	private Integer sex; //1-男 0-女
	private Date birthday;
	private String phone;
	private String email;
}
