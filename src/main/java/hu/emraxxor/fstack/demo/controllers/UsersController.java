package hu.emraxxor.fstack.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.emraxxor.fstack.demo.config.ApplicationUser;
import hu.emraxxor.fstack.demo.core.web.CurrentUserInfo;
import hu.emraxxor.fstack.demo.data.type.SimpleUser;
import hu.emraxxor.fstack.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UsersController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/info")
	public SimpleUser info() {
		return (SimpleUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
