/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.web;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.zhengxinacc.system.domain.Permission;
import com.zhengxinacc.system.service.PermissionService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午6:08:16
 * @version 1.0
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

	@Autowired
	private PermissionService permissionService;
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
		Page<Permission> pager = permissionService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		result.put("data", pager.getContent());
		
		return result;
	}
	
	@PostMapping("/save")
	public JSONObject save(@RequestBody Permission permission){
		
		JSONObject param = new JSONObject();
		param.put("id", permission.getId());
		param.put("name", permission.getName());
		param.put("key", permission.getKey());
		param.put("username", permission.getCreateUser());
		
		permissionService.save(param);
		
		return writeSuccess();
	}
	
	@PostMapping("/delete")
	public JSONObject delete(@RequestParam(value="ids[]") String[] ids){
		permissionService.delete(ids);
		return writeSuccess();
	}
}
