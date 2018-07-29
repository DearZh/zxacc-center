/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月28日 下午2:41:45
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionServiceTests {

	@Autowired
	PermissionService permissionService;
	@Autowired
	UserService userService;
	
	@Test
	public void testGetAuthorities(){
		User user = userService.findByUsername("ekozhan");
		List<GrantedAuthority> list = permissionService.getAuthorities(user);
		System.out.println(JSON.toJSONString(list));
		
		String granted = JSON.toJSONString(list);
		
		List<SimpleGrantedAuthority> list1 = JSON.parseArray(granted, SimpleGrantedAuthority.class);
		System.out.println(list1.size());
		System.out.println(list1.toString());
		list1.forEach(t -> {System.out.println(t.toString());});
		System.out.println(JSON.toJSONString(list1));
	}
}
