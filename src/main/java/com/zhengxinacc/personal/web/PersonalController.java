/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.personal.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.service.UserService;
import com.zhengxinacc.util.EncryptUtils;

/**
 * 个人中心
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月20日 下午12:34:59
 * @version 1.0
 */
@RestController
@RequestMapping("/personal")
public class PersonalController extends BaseController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 修改密码
	 * @author eko.zhan at 2018年1月20日 下午2:55:01
	 * @param oldPwd
	 * @param newPwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value="changePwd", method=RequestMethod.POST)
	public JSONObject changePwd(String oldPwd, String newPwd, HttpServletRequest request){
		User user = getCurrentUser(request);
		if (!EncryptUtils.verify(oldPwd, user.getPassword(), user.getSalt())) {
            return writeSuccess("原密码输入错误");
        }
		userService.changePwd(user, newPwd);
		return writeSuccess("密码修改成功，请重新登入");
	}

}
