/*
 * Power by www.xiaoi.com
 */
package com.zhengxinacc.system.user.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhengxinacc.config.BaseBean;
import com.zhengxinacc.system.permission.domain.Permission;
import com.zhengxinacc.system.role.domain.Role;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月23日 上午10:51:56
 * @version 1.0
 */
@Document(collection="sys_user")
@Getter
@Setter
public class User extends BaseBean implements UserDetails {
	/**
	 * @author eko.zhan at 2017年12月23日 下午3:23:39
	 */
	private static final long serialVersionUID = 110895296317295676L;
	@Id
	private String id;
	private String username;
	private String password;
	private String salt;
	private UserInfo userInfo;
	private List<Role> roles;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<Role> roles = getRoles();
        if (roles!=null){
        	for (Role role : roles) {
        		if (role.getPermissions()!=null){
        			for (Permission permission : role.getPermissions()){
		        		auths.add(new SimpleGrantedAuthority(permission.getName()));
		        	}
        		}
	        }
        }
        if (auths.size()==0){
        	auths.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return auths;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return false;
	}

}
