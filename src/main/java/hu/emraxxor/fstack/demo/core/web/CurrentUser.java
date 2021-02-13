package hu.emraxxor.fstack.demo.core.web;

import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class CurrentUser<T extends SimpleUser> implements CurrentUserInfo<T> {

	private Boolean isAuthenticated;

	private DefaultApplicationRole role;
	
	private T user;
	
	public Boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public DefaultApplicationRole getRole() {
		return role;
	}

	@Override
	public T getUser() {
		return user;
	}

}
