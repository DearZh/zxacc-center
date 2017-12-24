/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhengxinacc.ZxaccCenterApplication;
import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午4:18:35
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=ZxaccCenterApplication.class)
public class UserServiceTests {

	@Resource
	private UserService userService;
	
	@Test
	public void testSave(){
		User user = userService.findByUsername("ekozhan");
		System.out.println(user==null);
	}
}
