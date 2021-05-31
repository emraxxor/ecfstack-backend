package com.github.emraxxor.fstack.demo.data.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPasswordFormElement {

    @NotNull @NotEmpty
    private String oldPassword;

    @NotNull @NotEmpty
    private String newPassword;

    @NotNull @NotEmpty
    private String newPasswordConfirm;
}
