/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.web;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.domain.UserInfo;
import com.zhengxinacc.system.user.service.UserService;
import com.zhengxinacc.util.SystemKeys;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午2:10:25
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Resource
	private UserService userService;

	/**
	 * 加载数据
	 * @author eko.zhan at 2017年12月23日 下午7:01:13
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("/loadList")
	public JSONObject loadList(Integer page, Integer limit, String keyword){
		
		JSONObject param = new JSONObject();
		param.put("property", "createDate");
		param.put("keyword", keyword);
		Page<User> pager = userService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		result.put("data", pager.getContent());
		
		return result;
	}
	
	/**
	 * 保存用户
	 * @author eko.zhan at 2017年12月23日 下午7:01:05
	 * @param principal
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	public JSONObject save(Principal principal, HttpServletRequest request){
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		String usernameCN = request.getParameter("usernameCN");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		
		User user = null;
		if (StringUtils.isBlank(id)){
			user = new User();
			user.setCreateDate(new Date());
			user.setCreateUser(principal.getName());
		}else{
			user = userService.findOne(id);
		}
		user.setUsername(username);
		UserInfo userInfo = user.getUserInfo();
		if (userInfo==null){
			userInfo = new UserInfo();
		}
		userInfo.setUsername(usernameCN);
		userInfo.setSex(Integer.parseInt(sex));
		if (StringUtils.isNotBlank(birthday)){
			try {
				userInfo.setBirthday(DateUtils.parseDate(birthday, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		userInfo.setPhone(phone);
		userInfo.setEmail(email);
		user.setUserInfo(userInfo);
		
		try{
			user = userService.save(user);
			//如果是当前用户，则刷新session
			if (user.getId().equals(getCurrentUser(request).getId())){
				request.getSession().setAttribute(SystemKeys.CURRENT_USER, user);
			}
			return writeSuccess(user);
		}catch(UsernameNotFoundException e){
			return writeFailure(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return writeFailure(e.getMessage());
		}
	}
	
	@RequestMapping("/delete")
	public JSONObject delete(String id){
		userService.delete(id);
		return writeSuccess();
	}
	
	@RequestMapping("/loadData")
	public User loadData(HttpServletRequest request){
		return getCurrentUser(request);
	}
	/**
	 * 批量导入用户
	 * @author eko.zhan at 2018年1月7日 下午2:45:13
	 */
	@RequestMapping("/import")
	public JSONObject importUsers(MultipartHttpServletRequest request){
		MultipartFile file = request.getFile("userFile");
		userService.importUsers(file, getUsername(request));
		return writeSuccess();
	}
}
