/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.service.UserService;
import com.zhengxinacc.util.SystemKeys;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午10:32:53
 * @version 1.0
 */
@Component(value="successHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {
	
	private static final Log logger = LogFactory.getLog(MyAuthenticationSuccessHandler.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		//登录成功后，将数据写入 session
		
//		SecurityContextImpl securityContext = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//		Principal principal = (Principal)securityContext.getAuthentication().getPrincipal();
//		WebAuthenticationDetails details = (WebAuthenticationDetails)securityContext.getAuthentication().getDetails();
//		String username = securityContext.getAuthentication().getName();
		
		String username = authentication.getName();
		logger.debug(username);
		User user = userService.findByUsername(username);
		logger.debug(user);
		request.getSession().setAttribute(SystemKeys.CURRENT_USER, user);
		
		super.onAuthenticationSuccess(request, response, authentication);
		
	}

	
}
