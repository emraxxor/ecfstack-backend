package hu.emraxxor.fstack.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.emraxxor.fstack.demo.config.ApplicationUserRole;
import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import hu.emraxxor.fstack.demo.data.type.response.StatusResponse;
import hu.emraxxor.fstack.demo.entities.User;
import hu.emraxxor.fstack.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class PublicUsersController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PostMapping("")
	public ResponseEntity<?> registration(@Valid @RequestBody SimpleUser user) {
		if ( userService.findUserByEmail(user.getUserMail()) == null && userService.findUserByName(user.getUserName()) == null ) {
			User u = mapper.map(user, User.class);
			u.setUserPassword(encoder.encode(user.getUserPassword()));
			u.setRole(ApplicationUserRole.USER);
			userService.save(u);
			return ResponseEntity.status(HttpStatus.CREATED).body(StatusResponse.success());
		} 
		
		return ResponseEntity.badRequest().body(StatusResponse.error("UniqueConstraintException"));
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	
    @RequestMapping(value = "/{username}", method = RequestMethod.HEAD )
    public ResponseEntity<?> exists(@PathVariable String username) {
    	if ( userService.findUserByName(username) != null ) 
    		return ResponseEntity.ok().build();
    	
    	return ResponseEntity.notFound().build();
    }

	
}