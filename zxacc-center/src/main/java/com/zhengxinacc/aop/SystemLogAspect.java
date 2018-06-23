/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.common.util.SystemKeys;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.util.LogUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年5月19日 下午1:46:02
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
public class SystemLogAspect {

	/**
	 * 记录保存操作日志 
	 */
	@Before("(execution(* com.zhengxinacc.exam.*.service.*Service.save*(..)))"
			+ " or (execution(* com.zhengxinacc.exam.*.service.*Service.del*(..)))"
			+ " or (execution(* com.zhengxinacc.system.*.service.*Service.save*(..)))"
			+ " or (execution(* com.zhengxinacc.system.*.service.*Service.del*(..)))")
	public void callback(JoinPoint joinPoint){
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (servletRequestAttributes==null) return;
			HttpServletRequest request = servletRequestAttributes.getRequest();    
			if (request==null) return;
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(SystemKeys.CURRENT_USER);         	
			if (user!=null){
				JSONObject info = new JSONObject();
				info.put("username", user.getUsername());
				info.put("usernameCN", user.getUserInfo().getUsername());
				info.put("createDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				info.put("ip", LogUtils.getRemoteIp(request));
				String clazz = joinPoint.getTarget().getClass().getName();
				String method = joinPoint.getSignature().getName();
				info.put("method", clazz + "." + method);
				info.put("params", joinPoint.getArgs()!=null?joinPoint.getArgs().toString():"");
				LogUtils.getSystem().info(info.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}