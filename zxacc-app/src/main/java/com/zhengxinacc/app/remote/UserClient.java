package com.zhengxinacc.app.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhengxinacc.system.domain.User;

@FeignClient(name="zxacc-center-system")
public interface UserClient {
	
	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/user/findByUsername", method=RequestMethod.POST)
	public User findByUsername(@RequestParam("username") String username);

	/**
	 * 根据用户名获取用户信息，返回登录账号
	 * @param username
	 * @return
	 */
	@RequestMapping(value="/user/loadUserByUsername", method=RequestMethod.POST)
	public UserDetails loadUserByUsername(@RequestParam("username") String username);

	@RequestMapping(value="/user/verify", method=RequestMethod.POST)
	public User verify(@RequestParam("username") String username, @RequestParam("password") String password);
}
