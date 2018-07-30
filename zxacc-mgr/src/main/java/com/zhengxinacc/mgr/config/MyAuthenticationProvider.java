/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.mgr.remote.UserClient;
import com.zhengxinacc.system.domain.Role;
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
        JSONObject json = null;
		try {
			json = userClient.verify(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("用户名或密码错误");
		}
		if (json==null || StringUtils.isBlank(json.getString("id"))){
			throw new BadCredentialsException("用户名或密码错误");
		}
		UserDetails userDetails = JSON.toJavaObject(json, UserDetails.class);
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		json.getJSONArray("authorities").forEach(item -> {
			String key = JSON.parseObject(item.toString()).getString("authority");
			auths.add(new SimpleGrantedAuthority(key));
		}); 
		
        return new UsernamePasswordAuthenticationToken(userDetails, password, auths);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
