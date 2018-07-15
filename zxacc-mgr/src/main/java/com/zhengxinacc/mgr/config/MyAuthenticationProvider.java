/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.config;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.zhengxinacc.mgr.remote.UserClient;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午7:32:37
 * @version 1.0
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Resource
	private UserClient userClient;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials(); //用户登录输入的密码（明文传输）
        User user = null;
		try {
			user = userClient.verify(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("用户名或密码错误");
		}
		if (user==null || StringUtils.isBlank(user.getId())){
			throw new BadCredentialsException("用户名或密码错误");
		}
		
        return new UsernamePasswordAuthenticationToken(user, password, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
