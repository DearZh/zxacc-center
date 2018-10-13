/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.mgr.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年7月30日 下午7:31:58
 * @version 1.0
 */
@FeignClient(name="zxacc-zuul")
public interface RoleClient {

	@RequestMapping(value="/api-system/role/loadList", method=RequestMethod.POST)
	public JSONObject loadList(@RequestParam("page") Integer page, 
			@RequestParam("limit") Integer limit, 
			@RequestParam(value="keyword", required=false) String keyword);
}
