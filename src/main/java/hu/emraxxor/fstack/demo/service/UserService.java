package hu.emraxxor.fstack.demo.service;

import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import hu.emraxxor.fstack.demo.entities.User;
import hu.emraxxor.fstack.demo.repositories.UserRepository;
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
public class UserService extends BasicServiceAdapter<User, Long, UserRepository>  {

	public Optional<User> findUserByName(String name) {
		return repository.findByUserName(name);
	}
	
	public Optional<User> findUserByEmail(String email) {
		return repository.findByUserMail(email);
	}
	
	public User save(User u) {
		return repository.save(u);
	}
	
	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}
	
	public Optional<User> current() {
		var curr = (SimpleUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return findById(curr.getUserId());
	}

	@CheckForNull
	public User curr() {
		if ( current().isPresent() ) {
			return current().get();
		}

		return null;
	}

}
