/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.role.service;

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
import com.zhengxinacc.system.role.domain.Role;
import com.zhengxinacc.system.role.repository.RoleRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午1:13:14
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Page<Role> findAll(Integer page, Integer size, JSONObject data, Direction desc) {
		String property = data.getString("property");
		String keyword = data.getString("keyword");
		
		Order order = new Order(desc, property);
		Pageable pageable = new PageRequest(page-1, size, new Sort(order));
		
		return roleRepository.findAll(pageable);
	}

	@Override
	public void save(JSONObject param) {
		String id = param.getString("id");
		Role role = null;
		if (StringUtils.isBlank(id)){
			role = new Role();
			role.setCreateUser(param.getString("username"));
		}else{
			role = roleRepository.findOne(id);
		}
		role.setModifyUser(param.getString("username"));
		role.setName(param.getString("name"));
		role.setKey(param.getString("key"));
		
		roleRepository.save(role);
	}

	@Override
	public void delete(String id) {
		roleRepository.delete(id);
	}

}
