/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.remote;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月28日 下午1:50:50
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionClientTests {

	@Autowired
	PermissionClient permissionClient;

	@Autowired
	UserClient userClient;
	
	@Test
	public void testGetAuthorities(){
		User user = userClient.findByUsername("ekozhan");
		List<GrantedAuthority> list = permissionClient.getAuthorities(user);
		System.out.println(list.size());
	}
}
