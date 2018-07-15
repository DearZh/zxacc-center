/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.app.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhengxinacc.app.remote.UserClient;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月8日 上午11:55:22
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTests {

	@Autowired
	IndexController indexController;
	@Autowired
	UserClient userClient;
	
	@Test
	public void testLoadTaskList(){
		User user = userClient.findByUsername("ekozhan");
		indexController.loadTaskList(user);
	}
	
}
