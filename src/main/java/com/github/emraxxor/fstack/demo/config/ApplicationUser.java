package com.github.emraxxor.fstack.demo.config;

import java.util.Collection;
import java.util.List;

import com.github.emraxxor.fstack.demo.core.web.CurrentUserInfo;
import com.github.emraxxor.fstack.demo.core.web.CurrentWebUserSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.emraxxor.fstack.demo.data.type.SimpleUser;
import com.github.emraxxor.fstack.demo.entities.User;
import lombok.Data;

/**
 * 
 * @author Attila Barna
 *
 */
@Data
public class ApplicationUser implements UserDetails, CurrentWebUserSession<SimpleUser> {

	private final List<? extends GrantedAuthority> grantedAuthorities;
	
	private final String username;
	
	private final Long userId;
	
	private final String password;
	
	private final Boolean isAccountNonExpired;
	
	private final Boolean isAaccountNonLocked;
	
	private final Boolean isCredentialsNonExpired;
	
	private final Boolean isEnabled;
	
	private final User user;
	
	private CurrentUserInfo<SimpleUser> userInfo;
 	
	public ApplicationUser(List<? extends GrantedAuthority> grantedAuthorities, User u) {
		super();
		this.grantedAuthorities = grantedAuthorities;
		this.userId = u.getUserId();
		this.username = u.getUserName();
		this.password = u.getUserPassword();
		this.isAccountNonExpired = u.getIsActive();
		this.isAaccountNonLocked = u.getIsActive();
		this.isCredentialsNonExpired = u.getIsActive();
		this.isEnabled = u.getIsActive();
		this.user = u;
	}

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAaccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}


	@Override
	public CurrentUserInfo<SimpleUser> current() {
		return userInfo;
	}
	
}
