package com.github.emraxxor.fstack.demo.service;

import com.github.emraxxor.fstack.demo.config.ApplicationUserRole;
import com.github.emraxxor.fstack.demo.repositories.UserRepository;
import com.github.emraxxor.fstack.demo.config.ApplicationUser;
import com.github.emraxxor.fstack.demo.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author Attila Barna
 *
 */
@Service
public class ApplicationUserService implements UserDetailsService {

	private final UserRepository userRepository;

	public ApplicationUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserName(username);
		
		if ( user.isPresent() ) {
			var u = user.get();
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
