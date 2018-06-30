/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zhengxinacc.system.domain.Role;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 上午11:45:08
 * @version 1.0
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String>{

	public List<Role> findByUsersIn(List<User> userList);
}
