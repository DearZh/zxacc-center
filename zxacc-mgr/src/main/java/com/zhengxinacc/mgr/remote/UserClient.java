package com.zhengxinacc.mgr.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
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

	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/user/verify", method=RequestMethod.POST)
	public JSONObject verify(@RequestParam("username") String username, @RequestParam("password") String password);
	/**
	 * 获取用户列表
	 * @param page
	 * @param limit
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="/user/loadList", method=RequestMethod.POST)
	public JSONObject loadList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("keyword") String keyword);
}
