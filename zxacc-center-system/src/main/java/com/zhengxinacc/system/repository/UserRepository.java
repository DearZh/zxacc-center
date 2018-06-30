/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.zhengxinacc.system.domain.User;
import com.zhengxinacc.system.domain.UserInfo;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月23日 下午1:22:58
 * @version 1.0
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>{

	/**
	 * 根据指定的账号获取生效中的用户
	 * @author eko.zhan at 2018年5月13日 下午1:23:43
	 * @param username
	 * @return
	 */
	@Query("{'delFlag': 0, 'username': ?0}")
	public User findByUsername(String username);
	
	public Page<User> findByUsernameLike(String username, Pageable pageable);
	
	public Page<User> findByUserInfoLike(UserInfo userInfo, Pageable pageable);
}
