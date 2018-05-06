/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.permission.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.zhengxinacc.system.permission.domain.Permission;
import com.zhengxinacc.system.permission.repository.PermissionRepository;
import com.zhengxinacc.system.permission.service.PermissionService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午6:08:16
 * @version 1.0
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController {

	private final static Log logger = LogFactory.getLog(PermissionController.class);
	@Autowired
	private PermissionService permissionService;
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
		Page<Permission> pager = permissionService.findAll(page, limit, param, Direction.DESC);
		
		JSONObject result = new JSONObject();
		result.put("code", 0);
		result.put("message", "");
		result.put("count", pager.getTotalElements());
		
		JSONArray dataArr = new JSONArray();
		List<Permission> list = pager.getContent();
		for (Permission permission : list){
			JSONObject tmp = (JSONObject)JSONObject.toJSON(permission);
			tmp.put("createDate", DateFormatUtils.format(permission.getCreateDate(), "yyyy-MM-dd"));
			
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
		
		JSONObject param = new JSONObject();
		param.put("id", id);
		param.put("name", name);
		param.put("key", key);
		param.put("username", getUsername(request));
		
		permissionService.save(param);
		
		return writeSuccess();
	}
	
	@PostMapping("/delete")
	public JSONObject delete(@RequestParam(value="ids[]") String[] ids){
		permissionService.delete(ids);
		return writeSuccess();
	}
}
