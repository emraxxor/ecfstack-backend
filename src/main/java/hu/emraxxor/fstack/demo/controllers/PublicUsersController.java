package hu.emraxxor.fstack.demo.controllers;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import hu.emraxxor.fstack.demo.data.type.response.StatusResponse;
import hu.emraxxor.fstack.demo.entities.User;
import hu.emraxxor.fstack.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class PublicUsersController {

	private final UserService userService;
	
	private final ModelMapper mapper;
	
	private final PasswordEncoder encoder;

	public PublicUsersController(UserService userService, ModelMapper mapper, PasswordEncoder encoder) {
		this.userService = userService;
		this.mapper = mapper;
		this.encoder = encoder;
	}

	@PostMapping("")
	public ResponseEntity<?> registration(@Valid @RequestBody SimpleUser user) {
		if ( userService.findUserByEmail(user.getUserMail()).isEmpty() && userService.findUserByName(user.getUserName()).isEmpty()) {
			User u = mapper.map(user, User.class);
			u.setUserPassword(encoder.encode(user.getUserPassword()));
			userService.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body(StatusResponse.success());
		} 
		return ResponseEntity.badRequest().body(StatusResponse.error("UniqueConstraintException"));
	}
	
	
    @RequestMapping( value="/{username}", method = RequestMethod.HEAD)
    public ResponseEntity<?> exists(@PathVariable String username) {
    	if ( userService.findUserByName(username).isPresent() ) 
    		return ResponseEntity.ok().build();
    	
    	return ResponseEntity.notFound().build();
    }
	
}
