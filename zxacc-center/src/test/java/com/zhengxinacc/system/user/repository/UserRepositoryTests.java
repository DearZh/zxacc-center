/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.repository;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午4:42:40
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Resource
	private UserRepository userRepository;
	
	@Test
	public void testFindAll(){
		List<User> list = userRepository.findAll();
		System.out.println(list.size());
	}
	
	@Test
	public void testSave(){
		User user = new User();
		user.setUsername("1");
		userRepository.save(user);
	}
}
