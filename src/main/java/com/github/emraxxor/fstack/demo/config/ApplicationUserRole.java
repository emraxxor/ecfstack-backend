package com.github.emraxxor.fstack.demo.config;

import static com.github.emraxxor.fstack.demo.config.ApplicationPermission.*;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

/**
 * 
 * @author Attila Barna
 *
 */
public enum ApplicationUserRole {

	USER(Sets.newHashSet(USER_READ, USER_WRITE, USER_CREATE, USER_DELETE)),
	
	ADMIN(Sets.newHashSet(ADMIN_READ, ADMIN_WRITE, ADMIN_CREATE, ADMIN_DELETE, USER_READ, USER_WRITE, USER_CREATE, USER_DELETE));
	
	private final Set<ApplicationPermission> permissions;
	
	ApplicationUserRole(Set<ApplicationPermission> p) {
		this.permissions = p;
	}
	
	public List<SimpleGrantedAuthority> grantedAuthorities() {
		List<SimpleGrantedAuthority> ps = permissions.stream().map(p -> new SimpleGrantedAuthority(p.get())).collect(Collectors.toList());	
		ps.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return ps;
	}
}
