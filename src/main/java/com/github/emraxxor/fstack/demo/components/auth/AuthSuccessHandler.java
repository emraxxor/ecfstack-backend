package com.github.emraxxor.fstack.demo.components.auth;

import com.github.emraxxor.fstack.demo.config.ApplicationUser;
import com.github.emraxxor.fstack.demo.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Attila Barna
 *
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final ApplicationUser au = (ApplicationUser)authentication.getPrincipal();
        final User u = au.getUser();
	}

}
