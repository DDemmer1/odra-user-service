package de.demmer.dennis.odrauserservice.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Valid
@Getter
@Setter
public class CommentRequest {

    @NotEmpty
    private String commentText;

    @NotNull
    private Long mediaId;

    @NotNull
    private Integer start;

    @NotNull
    private Integer end;

    @NotNull
    private String color;

    @NotEmpty
    private String selectedText;




}
