package de.demmer.dennis.odrauserservice.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TopicDeleteRequest {

    @NotNull
    Long topicId;

    @NotNull
    Long metaId;
}
