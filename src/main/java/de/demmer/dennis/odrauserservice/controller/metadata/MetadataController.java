package de.demmer.dennis.odrauserservice.controller.metadata;

import de.demmer.dennis.odrauserservice.model.meta.Metadata;
import de.demmer.dennis.odrauserservice.model.meta.Topic;
import de.demmer.dennis.odrauserservice.payload.ApiResponse;
import de.demmer.dennis.odrauserservice.payload.TagRequest;
import de.demmer.dennis.odrauserservice.payload.TopicDeleteRequest;
import de.demmer.dennis.odrauserservice.payload.TopicRequest;
import de.demmer.dennis.odrauserservice.security.CurrentUser;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import de.demmer.dennis.odrauserservice.service.MetadataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/meta")
public class MetadataController {

    @Autowired
    MetadataService metadataService;


    @GetMapping("/{mediaId}")
    public Metadata getMetadataFromMediaId(@CurrentUser UserPrincipal currentUser, @PathVariable long mediaId) {
        return metadataService.getMetaOfMedia(mediaId);
    }

    @GetMapping("/topic/all/{topicName}")
    public List<Topic> getMetadataFromMediaId(@CurrentUser UserPrincipal currentUser, @PathVariable String topicName) {
        return metadataService.getAllTopicsByName(topicName);
    }


    @PostMapping("/topic")
    public ApiResponse addTopic(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TopicRequest topicRequest) {
        try {
            Topic topic = new Topic(topicRequest.getTopic(), new Date(), topicRequest.getMediaId());
            topic.setSetByUser(true);
            topic.setUserid(currentUser.getId());
            boolean topicAdded = metadataService.addTopic(topic,topicRequest.getMediaId());

            String msg = topicAdded ? "topic was added" : "topic was removed";
            return new ApiResponse(true, "Topic request at media: " + topicRequest.getMediaId() + ", " + msg);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Topic request at media: " + topicRequest.getMediaId() + " " + e.toString());
        }
    }



    @GetMapping("/topic/all")
    public Set<Topic> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        try {
            return metadataService.getAllTopics();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @PostMapping("topic/delete")
    public ApiResponse deleteTopic(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TopicDeleteRequest topicDeleteRequest){
        try {
            boolean deleted = metadataService.deleteTopic(topicDeleteRequest.getTopicId(),topicDeleteRequest.getMetaId());
            if(deleted){
                return new ApiResponse(true, "Topic deleted");
            } else {
                return new ApiResponse(false, "Topic was not deleted. Something went wrong.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Topic was not deleted. Something went wrong. " + e.toString());
        }

    }


}
