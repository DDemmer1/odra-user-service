package de.demmer.dennis.odrauserservice.service;


import de.demmer.dennis.odrauserservice.service.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SseService {

    @Autowired
    private JsonConverter converter;

    @Autowired
    private MetadataService metadataService;

    private List<SseEmitter> sseEmitters = new ArrayList<>();

    public SseEmitter subToMetaStream() {

        SseEmitter emitter = new SseEmitter(60000000L);
        emitter.onCompletion(() -> {
            sseEmitters.remove(emitter);
        });
        emitter.onError(throwable -> {
            sseEmitters.remove(emitter);
        });
        emitter.onTimeout(() -> {
            sseEmitters.remove(emitter);
        });
        sseEmitters.add(emitter);
        return emitter;
    }


    public void emmitMetadataUpdate(Long articleId) {
        sseEmitters.forEach(emitter -> {
            try {
                System.out.println("Sending update request to Article" + articleId);
              emitter.send(metadataService.getMetaOfMedia(articleId));
//                emitter.send("Updated meta", MediaType.TEXT_EVENT_STREAM);
            } catch (IOException e) {
                emitter.complete();
            }
        });
    }

}
