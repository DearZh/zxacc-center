/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.role.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.role.domain.Role;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午1:03:59
 * @version 1.0
 */
public interface RoleService {

	
	public Page<Role> findAll(Integer page, Integer size, JSONObject data, Direction desc);

	public void save(JSONObject param);

	public void delete(String id);

	public void delete(String[] ids);
}
