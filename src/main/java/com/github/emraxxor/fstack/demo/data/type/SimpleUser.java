package com.github.emraxxor.fstack.demo.data.type;


import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.github.emraxxor.fstack.demo.config.ApplicationUserRole;
import com.github.emraxxor.fstack.demo.entities.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SimpleUser extends FormElement<User> {

	private Long userId;
	
	@NotNull @NotBlank
    private String userName;

	@NotNull @NotBlank
	@IgnoreField
    private String userPassword;

	@NotNull @NotBlank
	@Email
    private String userMail;

	@NotNull @NotBlank
	private String firstName;

	@NotNull @NotBlank
    private String lastName;

	private String address;

	private String city;

	private String state;

	private Number zip;
	
	private ApplicationUserRole role;
	
	private LocalDateTime createdOn;

	public SimpleUser(User u) {
		super(u);
	}

}
