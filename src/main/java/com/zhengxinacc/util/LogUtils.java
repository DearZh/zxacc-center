/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.user.domain.User;

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
	
}
