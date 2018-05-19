/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.zhengxinacc.system.role.repository.RoleRepository;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.service.UserService;
import com.zhengxinacc.util.LogUtils;
import com.zhengxinacc.util.SystemKeys;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月24日 上午10:32:53
 * @version 1.0
 */
@Slf4j
@Component(value="successHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;
	
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
		User user = userService.findByUsername(username);
		log.debug(user.toString());
		request.getSession().setAttribute(SystemKeys.CURRENT_USER, user);
		LogUtils.infoLogin(user, getRemoteIp(request));
		
//		List<Role> list = roleRepository.findByUsersIn(Arrays.asList(new User[]{user}));
//		if (list!=null){
//			for (Role r : list){
//				if (r.getKey().contains("SYS_")){
//					super.setDefaultTargetUrl("/main");
//					break;
//				}
//			}
//		}
		super.onAuthenticationSuccess(request, response, authentication);
		
	}

	/**
	 * 获取请求的真实ip地址
	 * @author eko.zhan at 2018年5月19日 下午12:54:11
	 * @param request
	 * @return
	 */
	private static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0, index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
