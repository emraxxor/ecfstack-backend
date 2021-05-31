package com.github.emraxxor.fstack.demo.controllers;

import com.github.emraxxor.fstack.demo.data.type.FormElement;
import com.github.emraxxor.fstack.demo.data.type.SimpleUser;
import com.github.emraxxor.fstack.demo.data.type.response.StatusResponse;
import com.github.emraxxor.fstack.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
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
