package de.demmer.dennis.odrauserservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TagRequest {

    @NotNull
    private Long mediaId;

}
