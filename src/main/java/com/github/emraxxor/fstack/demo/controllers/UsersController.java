package com.github.emraxxor.fstack.demo.controllers;

import com.github.emraxxor.fstack.demo.data.type.*;
import com.github.emraxxor.fstack.demo.data.type.response.StatusResponse;
import com.github.emraxxor.fstack.demo.service.ProfileStorageService;
import com.github.emraxxor.fstack.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UsersController {

	private final UserService userService;
	
	private final ProfileStorageService profileStorage;
	
	private final ModelMapper mapper;

	private final PasswordEncoder encoder;

	@GetMapping("/info")
	public SimpleUser info() {
		return FormElement.convertTo( userService.curr(), SimpleUser.class );
	}
	
	@PutMapping("/image")
	public ResponseEntity<StatusResponse> image(@RequestBody ImageData data) {
		if ( data.getData() != null ) {
			var curr = userService.current().get();
			profileStorage.remove(curr.getImage());
			FileInfo finfo = profileStorage.storeFile(data.getData());
			curr.setImage(finfo.name());
			userService.save(curr);
			return ResponseEntity.ok(StatusResponse.success());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/image")
	public ResponseEntity<StatusResponse> image() {
		try {
			var curr = userService.current().get();
			return ResponseEntity.ok(StatusResponse.success(profileStorage.file(curr.getImage())));
		} catch (IOException e) {
			 return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping
	public ResponseEntity<StatusResponse> update(@RequestBody SimpleUser data) {
		SimpleUser curr = userService.principal();
		var persistent = userService.curr();
		if ( persistent != null ) {
			FormElement.update( data , persistent);
			if ( !persistent.getUserMail().equals(curr.getUserMail()) && userService.findUserByEmail(data.getUserMail()).isPresent() )
				return ResponseEntity.badRequest().body(StatusResponse.error(data));

			return ResponseEntity.ok(  StatusResponse.success( FormElement.convertTo( userService.save(persistent)  , SimpleUser.class ) ));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/password")
	public ResponseEntity<StatusResponse> updatePassword(@Valid @RequestBody UserPasswordFormElement data) {
		var user = userService.curr();
		if ( user != null ) {
			if (encoder.matches(data.getOldPassword(), user.getUserPassword())) {
				if (data.getNewPassword().length() > 3 && data.getNewPassword().equals(data.getNewPasswordConfirm())) {
					user.setUserPassword(encoder.encode(data.getNewPassword()));
					return ResponseEntity.ok(StatusResponse.success(FormElement.convertTo(userService.save(user), SimpleUser.class)));
				}
			}
		}
		return ResponseEntity.ok(StatusResponse.error(FormElement.convertTo( user, SimpleUser.class)));
	}
}
