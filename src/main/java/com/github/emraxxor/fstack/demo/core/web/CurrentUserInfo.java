package com.github.emraxxor.fstack.demo.core.web;

/**
 * @author Attila Barna
 * @param <T>
 */
public interface CurrentUserInfo<T> {

	Boolean isAuthenticated();
	
	DefaultApplicationRole getRole();
	
	T getUser();
}
