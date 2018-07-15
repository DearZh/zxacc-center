/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.domain.User;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年5月19日 下午12:37:27
 * @version 1.0
 */
public class LogUtils {

	/**
	 * 获取记录登录的日志
	 * @author eko.zhan at 2018年5月19日 下午12:38:38
	 * @return
	 */
	public static Log getLogin() {
		Log logger = LogFactory.getLog("FILE_LOGIN");
		return logger;
	}
	
	/**
	 * 获取用于系统操作记录的日志
	 * @author eko.zhan at 2018年5月19日 下午12:39:18
	 * @return
	 */
	public static Log getSystem() {
		Log logger = LogFactory.getLog("FILE_SYSTEM");
		return logger;
	}
	
	/**
	 * 记录登录数据
	 * @author eko.zhan at 2018年5月19日 下午12:53:05
	 * @param user
	 * @param ip
	 */
	public static void infoLogin(User user, String ip){
		JSONObject info = new JSONObject();
		info.put("username", user.getUsername());
		info.put("usernameCN", user.getUserInfo().getUsername());
		info.put("createDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		info.put("ip", ip);
		getLogin().info(info);
	}
	
	/**
	 * 获取请求的真实ip地址
	 * @author eko.zhan at 2018年5月19日 下午12:54:11
	 * @param request
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
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
