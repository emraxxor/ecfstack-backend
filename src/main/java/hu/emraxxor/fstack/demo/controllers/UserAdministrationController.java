package hu.emraxxor.fstack.demo.controllers;

import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.emraxxor.fstack.demo.data.type.FormElement;
import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import hu.emraxxor.fstack.demo.data.type.response.StatusResponse;
import hu.emraxxor.fstack.demo.service.UserService;

@RestController
@RequestMapping("/api/admin/user")
@AllArgsConstructor
public class UserAdministrationController {

	private final UserService userService;

	@GetMapping("/ordered")
	public ResponseEntity<?> listAll(@RequestParam("page") int page) {
		return ResponseEntity.ok(
				  StatusResponse.success(
						  userService.getRepository().findAllByOrderByUserNameAsc(PageRequest.of(page, 20))
						  .stream()
						  .map(e -> FormElement.convertTo(e, SimpleUser.class) ).collect(Collectors.toList())
				  )
			   );
	}
	
	@GetMapping("/total")
	public ResponseEntity<?> total() {
		return ResponseEntity.ok(
					  StatusResponse.success(
							  userService.count()
					  )
	    );
	}


}
