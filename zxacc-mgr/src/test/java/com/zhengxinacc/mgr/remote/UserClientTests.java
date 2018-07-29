/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.remote;

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
 * @date 2018年7月28日 下午12:03:53
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserClientTests {

	@Autowired
	UserClient userClient;
	
	@Test
	public void testLoadUserByUsername(){
		UserDetails userDetails = userClient.loadUserByUsername("ekozhan");
		System.out.println(userDetails);
		
	}
	
	@Test
	public void testVerify(){
		JSONObject json = userClient.verify("ekozhan", "888888");
		UserDetails userDetails3 = JSON.toJavaObject(json, UserDetails.class);
		
		System.out.println(userDetails3);
		userDetails3.getAuthorities().forEach(t->System.out.println(t.getAuthority()));
	}
}
