/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.role.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.config.BaseController;
import com.zhengxinacc.system.role.domain.Role;
import com.zhengxinacc.system.role.service.RoleService;

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
	@GetMapping("/loadList")
	public JSONObject loadList(Integer page, Integer limit, String keyword){
		
		JSONObject param = new JSONObject();
		param.put("property", "createDate");
		param.put("keyword", keyword);
		Page<Role> pager = roleService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		
		JSONArray dataArr = new JSONArray();
		List<Role> list = pager.getContent();
		List<String> tmpList = new ArrayList<String>();
		for (Role role : list){
			JSONObject tmp = (JSONObject)JSONObject.toJSON(role);
			tmp.put("createDate", DateFormatUtils.format(role.getCreateDate(), "yyyy-MM-dd"));
			tmpList.clear();
			if (role.getPermissions()!=null){
				role.getPermissions().forEach(permission -> {
					tmpList.add(permission.getName());
				});
			}
			tmp.put("permissionNames", tmpList.toArray(new String[tmpList.size()]));
			tmpList.clear();
			if (role.getUsers()!=null){
				role.getUsers().forEach(user -> {
					tmpList.add(user.getUserInfo().getUsername());
				});
			}
			tmp.put("userNames", tmpList.toArray(new String[tmpList.size()]));
			dataArr.add(tmp);
		}
		
		
		result.put("data", dataArr);
		
		return result;
	}
	
	@PostMapping("/save")
	public JSONObject save(HttpServletRequest request){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String key = request.getParameter("key");
		String permissionIds = request.getParameter("permissionIds");
		String userIds = request.getParameter("userIds");
		
		JSONObject param = new JSONObject();
		param.put("id", id);
		param.put("name", name);
		param.put("key", key);
		param.put("username", getUsername(request));
		param.put("permissionIds", permissionIds);
		param.put("userIds", userIds);
		
		roleService.save(param);
		
		return writeSuccess();
	}
	
	@PostMapping("/delete")
	public JSONObject delete(@RequestParam(value="ids[]") String[] ids){
		roleService.delete(ids);
		return writeSuccess();
	}
}
