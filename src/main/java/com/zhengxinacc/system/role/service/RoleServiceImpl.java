/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.role.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.zhengxinacc.system.role.repository.RoleRepository;
import com.zhengxinacc.system.user.domain.User;
import com.zhengxinacc.system.user.repository.UserRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年1月10日 下午1:13:14
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private UserRepository userRepository;
	
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
			roleRepository.save(role);
		}else{
			role = roleRepository.findOne(id);
		}
		role.setModifyUser(param.getString("username"));
		role.setName(param.getString("name"));
		role.setKey(param.getString("key"));
		
		String permissionIds = param.getString("permissionIds");
		String userIds = param.getString("userIds");
		if (StringUtils.isNotBlank(permissionIds)){
			List<Permission> permissionList = new ArrayList<Permission>();
			//TODO 思考循环查询 与 findByIdIn 的效率
			String[] permissionIdArr = permissionIds.split(",");
			for (String permissionId : permissionIdArr){
				Permission permission = permissionRepository.findOne(permissionId);
				permissionList.add(permission);
			}
			role.setPermissions(permissionList);
		}
		if (StringUtils.isNotBlank(userIds)){
			List<User> userList = new ArrayList<User>();
			String[] userIdArr = userIds.split(",");
			for (String userId : userIdArr){
				User user = userRepository.findOne(userId);
				userList.add(user);
//				if (user.getRoles()!=null){
//					List<Role> roleList = user.getRoles();
//					Set<Role> userSet = new HashSet<Role>();
//					userSet.addAll(user.getRoles());
//					if (userSet.add(role)){
//						roleList.add(role);
//						user.setRoles(roleList);
//					}
//				}else{
//					user.setRoles(Arrays.asList(new Role[]{role}));
//				}
				userRepository.save(user);
			}
			role.setUsers(userList);
		}
		
		roleRepository.save(role);
	}

	@Override
	public void delete(String id) {
		roleRepository.delete(id);
	}

}
