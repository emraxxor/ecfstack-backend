package hu.emraxxor.fstack.demo.core.web;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class CurrentUser<T> implements CurrentUserInfo<T> {

	private Boolean isAuthenticated;

	private DefaultApplicationRole role;
	
	private String userMail;
	
	private Date registrationDate;
	
	private String userName;
	
	private Long userId;
	
	private T user;
	
	public Boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public DefaultApplicationRole getRole() {
		return role;
	}

	@Override
	public String getUserMail() {
		return userMail;
	}

	@Override
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public String userName() {
		return userName;
	}

	@Override
	public Long userId() {
		return userId;
	}

	@Override
	public T getUser() {
		return user;
	}

}
