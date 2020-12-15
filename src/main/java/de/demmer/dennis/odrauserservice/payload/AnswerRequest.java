package de.demmer.dennis.odrauserservice.payload;

import de.demmer.dennis.odrauserservice.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Valid
@Getter
@Setter
public class AnswerRequest {

    @NotEmpty
    private String text;

    @NotNull
    private Long mediaId;

    @NotNull
    private Long commentId;


}
