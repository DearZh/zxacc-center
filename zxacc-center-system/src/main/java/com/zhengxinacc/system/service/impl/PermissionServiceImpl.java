/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhengxinacc.system.domain.Permission;
import com.zhengxinacc.system.domain.Role;
import com.zhengxinacc.system.domain.User;
import com.zhengxinacc.system.repository.PermissionRepository;
import com.zhengxinacc.system.repository.RoleRepository;
import com.zhengxinacc.system.service.PermissionService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午6:17:22
 * @version 1.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleRepository roleRepository;

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

	@Override
	public void delete(String[] ids) {
		List<String> idList = Arrays.asList(ids);
		idList.forEach(id -> permissionRepository.delete(id));
	}

	@Override
	public List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<Role> roles = roleRepository.findByUsersIn(Arrays.asList(new User[]{user}));//user.getRoles();
        if (roles!=null){
        	for (Role role : roles) {
        		if (role.getPermissions()!=null){
        			role.getPermissions().forEach(permission -> {
        				if (!permission.getKey().startsWith("ROLE_")){
        					permission.setKey("ROLE_" + permission.getKey());
        				}
        				auths.add(new SimpleGrantedAuthority(permission.getKey()));
        			});
        		}
	        }
        }
        if (auths.size()==0){
        	auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return auths;
	}

}
