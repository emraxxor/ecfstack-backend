package hu.emraxxor.fstack.demo.components.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hu.emraxxor.fstack.demo.config.ApplicationUser;
import hu.emraxxor.fstack.demo.entities.User;

/**
 * 
 * @author Attila Barna
 *
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final ApplicationUser au = (ApplicationUser)authentication.getPrincipal();
        final User u = au.getUser();
	}

}
