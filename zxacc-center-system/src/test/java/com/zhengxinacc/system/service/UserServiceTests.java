/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月28日 下午2:41:45
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	PermissionService permissionService;
	@Autowired
	UserService userService;
	
	@Test
	public void testLoadUserByUsername(){
		UserDetails userDetails = userService.loadUserByUsername("ekozhan");
		String userDetailsString = JSON.toJSONString(userDetails);
		JSONObject json = (JSONObject)JSON.toJSON(userDetails);
		
		System.out.println(userDetailsString);
		
		System.out.println("======================================");
		UserDetails userDetails3 = JSON.toJavaObject(json, UserDetails.class);
		System.out.println(userDetails3);
		userDetails3.getAuthorities().forEach(t->System.out.println(t.getAuthority()));
	}
}
