/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月23日 下午1:22:58
 * @version 1.0
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{

	public User findByUsername(String username);
	
}
