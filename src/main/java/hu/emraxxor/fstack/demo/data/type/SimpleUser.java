package hu.emraxxor.fstack.demo.data.type;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SimpleUser {

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

}
