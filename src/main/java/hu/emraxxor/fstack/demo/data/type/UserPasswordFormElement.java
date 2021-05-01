package hu.emraxxor.fstack.demo.data.type;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserPasswordFormElement {

    @NotNull @NotEmpty
    private String oldPassword;

    @NotNull @NotEmpty
    private String newPassword;

    @NotNull @NotEmpty
    private String newPasswordConfirm;
}
