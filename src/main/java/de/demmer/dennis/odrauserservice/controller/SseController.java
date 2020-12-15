package de.demmer.dennis.odrauserservice.controller;

import de.demmer.dennis.odrauserservice.service.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {

    @Autowired
    SseService sseService;

    @GetMapping(path = "sse/subscribe/meta", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamComments() {
        return sseService.subToMetaStream();
    }


}
