package hu.emraxxor.fstack.demo.data.type;


import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SimpleUser {

	private Long userId;
	
	@NotNull @NotBlank
    private String userName;

	@NotNull @NotBlank
    private String userPassword;

	@NotNull @NotBlank
	@Email
    private String userMail;

	@NotNull @NotBlank
	private String firstName;

	@NotNull @NotBlank
    private String lastName;
	
	private String role;
	
	private LocalDateTime createdOn;

}
