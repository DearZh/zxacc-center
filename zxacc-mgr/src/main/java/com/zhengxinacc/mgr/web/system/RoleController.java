/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.web.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.common.config.BaseController;
import com.zhengxinacc.mgr.remote.RoleClient;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月30日 下午7:28:39
 * @version 1.0
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

	@Autowired
	RoleClient roleClient;
	
	@GetMapping("/loadList")
	public JSONObject loadList(@RequestParam("page") Integer page, 
			@RequestParam("limit") Integer limit, 
			@RequestParam(value="keyword", required=false) String keyword){
		return roleClient.loadList(page, limit, keyword);
	}
}
