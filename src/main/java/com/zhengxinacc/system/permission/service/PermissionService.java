/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.permission.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.permission.domain.Permission;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午6:17:22
 * @version 1.0
 */
public interface PermissionService {

	public Page<Permission> findAll(Integer page, Integer size, JSONObject data, Direction desc);

	public void save(JSONObject param);

	public void delete(String id);

}
