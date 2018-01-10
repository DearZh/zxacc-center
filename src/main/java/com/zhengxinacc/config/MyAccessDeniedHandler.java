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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 403 Forbidden
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午1:42:56
 * @version 1.0
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	private static Log logger = LogFactory.getLog(MyAccessDeniedHandler.class);
	
	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
            logger.info("User '" + auth.getName()
                    + "' attempted to access the protected URL: "
                    + request.getRequestURI());
        }

		response.sendRedirect(request.getContextPath() + "/403");
	}

}
