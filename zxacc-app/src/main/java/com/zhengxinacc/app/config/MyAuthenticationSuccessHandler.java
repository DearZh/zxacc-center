/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.app.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zhengxinacc.app.remote.UserClient;
import com.zhengxinacc.app.util.LogUtils;
import com.zhengxinacc.common.util.SystemKeys;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午10:32:53
 * @version 1.0
 */
@Slf4j
@Component(value="successHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

	@Autowired
	UserClient userClient;
	
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
		log.debug(username);
		User user = userClient.findByUsername(username);
		log.debug(user.toString());
		request.getSession().setAttribute(SystemKeys.CURRENT_USER, user);
		LogUtils.infoLogin(user, LogUtils.getRemoteIp(request));
		
//		List<Role> list = roleRepository.findByUsersIn(Arrays.asList(new User[]{user}));
//		if (list!=null){
//			for (Role r : list){
//				if (r.getKey().contains("SYS_")){
//					super.setDefaultTargetUrl("/main");
//					break;
//				}
//			}
//		}
		//Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1
		//Mozilla/5.0 (Linux; Android 7.0; FRD-AL00 Build/HUAWEIFRD-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044034 Mobile Safari/537.36 MicroMessenger/6.6.7.1321(0x26060737) NetType/WIFI Language/zh_CN
		//Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.170 Safari/537.36
//		String userAgent = request.getHeader("User-Agent");
//		if (userAgent.indexOf("Windows NT")==-1){ //移动端
//			super.setDefaultTargetUrl("/");
//		}else{
//			super.setDefaultTargetUrl("/main");
//		}
		super.onAuthenticationSuccess(request, response, authentication);
		
	}
}
