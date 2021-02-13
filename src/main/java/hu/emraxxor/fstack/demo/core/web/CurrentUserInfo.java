package hu.emraxxor.fstack.demo.core.web;

public interface CurrentUserInfo<T> {

	public Boolean isAuthenticated();
	
	public DefaultApplicationRole getRole();
	
	public T getUser();
}
