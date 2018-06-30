/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.common.config.BaseController;
import com.zhengxinacc.system.domain.User;
import com.zhengxinacc.system.service.UserService;

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
	@ApiOperation(value="根据指定的参数获取用户数据列表", notes="支持分页")
	@ApiImplicitParams({
		@ApiImplicitParam(name="page", value="当前页数，从1开始", required=true, dataType="Integer", paramType="query"),
		@ApiImplicitParam(name="limit", value="每页显示多少条数据", required=true, dataType="Integer", paramType="query"),
		@ApiImplicitParam(name="keyword", value="关键词", required=false, dataType="String", paramType="query")
	})
	@PostMapping("/loadList")
	public JSONObject loadList(@RequestParam("page") Integer page, 
			@RequestParam("limit") Integer limit, 
			@RequestParam(value="keyword", required=false) String keyword){
		
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
	@PostMapping("/save")
	public JSONObject save(@RequestBody User user){
		String id = user.getId();
		
		if (StringUtils.isBlank(id)){
			user.setCreateDate(new Date());
		}
		
		try{
			user = userService.save(user);
			return writeSuccess(user);
		}catch(UsernameNotFoundException e){
			return writeFailure(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return writeFailure(e.getMessage());
		}
	}
	
	@PostMapping("/delete")
	public JSONObject delete(@RequestParam("id") String id){
		userService.delete(id);
		return writeSuccess();
	}
	
	/**
	 * 批量导入用户
	 * @author eko.zhan at 2018年1月7日 下午2:45:13
	 */
	@PostMapping("/import")
	public JSONObject importUsers(@RequestParam("userFile") MultipartFile file, @RequestParam("username") String username){
		userService.importUsers(file, username);
		return writeSuccess();
	}
	
	@PostMapping("/findByUsername")
	public User findByUsername(@RequestParam("username") String username){
		return userService.findByUsername(username);
	}
}
