package hu.emraxxor.fstack.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.emraxxor.fstack.demo.config.ApplicationUser;
import hu.emraxxor.fstack.demo.config.ApplicationUserRole;
import hu.emraxxor.fstack.demo.entities.User;
import hu.emraxxor.fstack.demo.repositories.UserRepository;

/**
 * 
 * @author Attila Barna
 *
 */
@Service
public class ApplicationUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUserName(username);
		
		if ( u != null ) {
			List<? extends GrantedAuthority> role = null;
			if ( u.getRole().equals( ApplicationUserRole.USER ) ) {
				role = ApplicationUserRole.USER.grantedAuthorities();
			} else if ( u.getRole().equals( ApplicationUserRole.ADMIN )  )  {
				role = ApplicationUserRole.ADMIN.grantedAuthorities();
			}
			
			return new ApplicationUser(role,u);
		} 
		return null;
	}
	

}
