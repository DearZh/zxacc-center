/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.config;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.service.UserService;
import com.zhengxinacc.util.EncryptUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午7:32:37
 * @version 1.0
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Resource
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = (String) authentication.getCredentials(); //用户登录输入的密码（明文传输）
        UserDetails userDetails = userService.loadUserByUsername(username); //密文
        if (userDetails==null){
        	throw new BadCredentialsException("用户名不存在");
        }
        User user = (User) userDetails;
        if (EncryptUtils.verify(password, user.getPassword(), user.getSalt())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
