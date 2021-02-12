package hu.emraxxor.fstack.demo.core.web;

import java.util.Date;

public interface CurrentUserInfo<T> {

	public Boolean isAuthenticated();
	
	public DefaultApplicationRole getRole();
	
	public String getUserMail();
	
	public Date getRegistrationDate();

	public String userName();
	
	public Long userId();
	
	public T getUser();
}
