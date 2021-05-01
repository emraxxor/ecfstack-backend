package hu.emraxxor.fstack.demo.core.web;

public interface CurrentWebUserSession<T> {

	/**
	 * Information about the current user
	 */
	CurrentUserInfo<T> current();
}
