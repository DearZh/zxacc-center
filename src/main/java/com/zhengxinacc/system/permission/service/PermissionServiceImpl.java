/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.permission.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.permission.domain.Permission;
import com.zhengxinacc.system.permission.repository.PermissionRepository;
import com.zhengxinacc.system.role.domain.Role;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午6:17:22
 * @version 1.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Page<Permission> findAll(Integer page, Integer size, JSONObject data, Direction desc) {
		String property = data.getString("property");
		String keyword = data.getString("keyword");
		
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		
		return permissionRepository.findAll(pageable);
	}

	@Override
	public void save(JSONObject param) {
		String id = param.getString("id");
		Permission permission = null;
		if (StringUtils.isBlank(id)){
			permission = new Permission();
			permission.setCreateUser(param.getString("username"));
		}else{
			permission = permissionRepository.findOne(id);
		}
		permission.setModifyUser(param.getString("username"));
		permission.setName(param.getString("name"));
		permission.setKey(param.getString("key"));
		
		permissionRepository.save(permission);
	}

	@Override
	public void delete(String id) {
		permissionRepository.delete(id);
	}
	
	

}
