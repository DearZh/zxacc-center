/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.user.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月23日 下午3:45:38
 * @version 1.0
 */
public interface UserService extends UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username);
	/**
	 * 保存用户信息
	 * @author eko.zhan at 2017年12月23日 下午3:26:49
	 * @param user
	 */
	public User save(User user);
	/**
	 * 根据用户主键id获取用户
	 * @author eko.zhan at 2017年12月23日 下午3:27:12
	 * @param id
	 * @return
	 */
	public User findOne(String id);
	/**
	 * 根据用户登录名称获取用户
	 * @author eko.zhan at 2017年12月23日 下午3:30:21
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	/**
	 * 获取用户数据分页
	 * @author eko.zhan at 2017年12月23日 下午7:15:19
	 * @param page
	 * @param size
	 * @param property
	 * @param desc
	 * @return
	 */
	public Page<User> findAll(Integer page, Integer size, JSONObject data, Direction desc);
	/**
	 * 删除用户
	 * @author eko.zhan at 2017年12月23日 下午7:15:33
	 * @param id
	 */
	public void delete(String id);
	/**
	 * 导入用户
	 * @author eko.zhan at 2018年1月7日 下午2:58:35
	 * @param file
	 */
	public void importUsers(MultipartFile file, String username);
}
