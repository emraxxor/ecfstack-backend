package hu.emraxxor.fstack.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.emraxxor.fstack.demo.entities.User;
import hu.emraxxor.fstack.demo.repositories.UserRepository;

/**
 * 
 * @author attila
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository repository;
	
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

}
