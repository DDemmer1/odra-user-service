package de.demmer.dennis.odrauserservice.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Valid
@Getter
@Setter
public class ChangePasswordRequest {


    @NotBlank
    private String newPassword;

    @NotBlank
    private String oldPassword;



}
