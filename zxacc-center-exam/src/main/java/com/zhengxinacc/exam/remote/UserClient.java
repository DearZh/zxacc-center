/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.exam.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月1日 上午10:30:07
 * @version 1.0
 */
@Service
@FeignClient("zxacc-center-system")
public interface UserClient {
	
	@RequestMapping(value="/user/findByUsername", method=RequestMethod.POST)
	public User findByUsername(@RequestParam("username") String username);
	
	@RequestMapping(value="/user/findByUserId", method=RequestMethod.POST)
	public User findByUserId(@RequestParam("id") String id);
}
