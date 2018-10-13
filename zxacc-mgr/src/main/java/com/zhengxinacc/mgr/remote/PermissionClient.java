/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.remote;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月28日 下午1:44:16
 * @version 1.0
 */
@FeignClient(name="zxacc-zuul")
public interface PermissionClient {
	
	@RequestMapping(value="/api-system/permission/getAuthorities", method=RequestMethod.POST)
	public List<GrantedAuthority> getAuthorities(@RequestBody User user);
}
