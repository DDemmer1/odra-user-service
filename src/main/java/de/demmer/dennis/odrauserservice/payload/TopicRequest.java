package de.demmer.dennis.odrauserservice.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TopicRequest {

    @NotNull
    private Long mediaId;

    @NotEmpty
    private String topic;


}
