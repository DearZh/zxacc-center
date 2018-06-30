/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.web;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.common.config.BaseController;
import com.zhengxinacc.system.domain.Role;
import com.zhengxinacc.system.service.RoleService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 下午2:10:25
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	@Resource
	private RoleService roleService;

	/**
	 * 加载数据
	 * @author eko.zhan at 2017年12月23日 下午7:01:13
	 * @param page
	 * @param limit
	 * @return
	 */
	@PostMapping("/loadList")
	public JSONObject loadList(@RequestParam("page") Integer page, 
			@RequestParam("limit") Integer limit, 
			@RequestParam(value="keyword", required=false) String keyword){
		
		JSONObject param = new JSONObject();
		param.put("property", "createDate");
		param.put("keyword", keyword);
		Page<Role> pager = roleService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		
		result.put("data", pager.getContent());
		
		return result;
	}
	
	@PostMapping("/save")
	public JSONObject save(@RequestBody Role role){
		
		JSONObject param = new JSONObject();
		param.put("id", role.getId());
		param.put("name", role.getName());
		param.put("key", role.getKey());
		param.put("username", role.getCreateUser());
		param.put("permissionIds", role.getPermissions());
		param.put("userIds", role.getUsers());
		
		roleService.save(param);
		
		return writeSuccess();
	}
	
	@PostMapping("/delete")
	public JSONObject delete(@RequestParam(value="ids[]") String[] ids){
		roleService.delete(ids);
		return writeSuccess();
	}
}
