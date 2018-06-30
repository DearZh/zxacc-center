/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.repository;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhengxinacc.system.domain.Role;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年6月27日 下午8:49:46
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTests {

	@Value("${spring.data.mongodb.uri}")
	String uri;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Resource
	UserRepository userRepository;
	
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Before
	public void setup(){
		System.out.println(uri);
	}
	
	@Test
	public void testFindAll(){
		List<User> list1 = mongoTemplate.findAll(User.class);
		System.out.println(list1.size());
		
		List<Role> list = roleRepository.findAll();
		System.out.println(list.size());
		
		List<User> list2 = userRepository.findAll();
		System.out.println(list2.size());
	}
	
	@Test
	public void testSave(){
		Role role = new Role();
		role.setKey("A");
		role.setName("A");
		roleRepository.save(role);
		
		System.out.println(1);
		List<Role> list = roleRepository.findAll();
		System.out.println(list.size());
		
	}
	
	@Test
	public void testDelete(){
		roleRepository.delete("");
	}
	
	
}
