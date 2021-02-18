package hu.emraxxor.fstack.demo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.emraxxor.fstack.demo.data.type.FormElement;
import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import hu.emraxxor.fstack.demo.data.type.response.StatusResponse;
import hu.emraxxor.fstack.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UsersController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/info")
	public SimpleUser info() {
		var curr = (SimpleUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return FormElement.convertTo( userService.findById( curr.getUserId() ).get(), SimpleUser.class );
	}
	
	@PutMapping
	public ResponseEntity<StatusResponse> update(@RequestBody SimpleUser data) {
		SimpleUser curr = (SimpleUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var u = userService.findById( curr.getUserId() );
		if ( u.isPresent() ) {
			var persistent = u.get();
			FormElement.update( data , persistent);
			
			if ( !persistent.getUserMail().equals(curr.getUserMail()) && userService.findUserByEmail(data.getUserMail()).isPresent() ) 
				return ResponseEntity.badRequest().body(StatusResponse.error(data));
	
			return ResponseEntity.ok(  StatusResponse.success( FormElement.convertTo( userService.save(persistent)  , SimpleUser.class ) ));
		}
		 return ResponseEntity.notFound().build();
	}
}
