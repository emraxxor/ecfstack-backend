package com.github.emraxxor.fstack.demo.service;

import com.github.emraxxor.fstack.demo.entities.UserLog;
import com.github.emraxxor.fstack.demo.repositories.UserLogRepository;
import com.github.emraxxor.fstack.demo.repositories.UserRepository;
import com.github.emraxxor.fstack.demo.data.type.SimpleUser;
import com.github.emraxxor.fstack.demo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.CheckForNull;
import java.util.Optional;

/**
 * 
 * @author attila
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService extends BasicServiceAdapter<User, Long, UserRepository>  {

	// TEST
	private final UserRepository repository;

	private final UserLogRepository userLogRepository;

	public Optional<User> findUserByName(String name) {
		return repository.findByUserName(name);
	}
	
	public Optional<User> findUserByEmail(String email) {
		return repository.findByUserMail(email);
	}
	
	public User save(User u) {
		return repository.save(u);
	}

	public UserLog save(UserLog u) {
		return userLogRepository.save(u);
	}

	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<User> current() {
		var curr = (SimpleUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return findById(curr.getUserId());
	}

	public SimpleUser principal() {
		return (SimpleUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}


	@CheckForNull
	public User curr() {
		if ( current().isPresent() ) {
			return current().get();
		}

		return null;
	}

}
