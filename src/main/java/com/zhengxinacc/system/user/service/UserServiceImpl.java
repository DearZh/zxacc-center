/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.service;

import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.repository.UserRepository;
import com.zhengxinacc.util.EncryptUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月23日 下午3:47:24
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
	/* 默认密码 */
	private final static String DEFAULT_PASSWORD = "888888";

	private final static Log logger = LogFactory.getLog(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        logger.debug("username:" + user.getUsername()+";password:" + user.getPassword());
        return user;
	}

	@Override
	public User save(User user) {
		//用户密码为空，初始化
		if (StringUtils.isBlank(user.getPassword())){
			String salt = String.valueOf(Calendar.getInstance().getTimeInMillis());
			user.setSalt(Base64.encode(salt.getBytes())); //加密储存秘钥
			user.setPassword(EncryptUtils.encode(DEFAULT_PASSWORD, salt));
		}
		return userRepository.save(user);
	}

	@Override
	public User findOne(String id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Page<User> findAll(Integer page, Integer size, String property, Direction desc) {
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		return userRepository.findAll(pageable);
	}

	@Override
	public void delete(String id) {
		userRepository.delete(id);
	}

}
