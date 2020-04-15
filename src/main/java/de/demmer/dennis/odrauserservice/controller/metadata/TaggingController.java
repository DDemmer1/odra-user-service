package de.demmer.dennis.odrauserservice.controller.metadata;

import de.demmer.dennis.odrauserservice.model.meta.tag.Flag;
import de.demmer.dennis.odrauserservice.model.meta.tag.Star;
import de.demmer.dennis.odrauserservice.payload.ApiResponse;
import de.demmer.dennis.odrauserservice.payload.TagRequest;
import de.demmer.dennis.odrauserservice.security.CurrentUser;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import de.demmer.dennis.odrauserservice.service.MetadataService;
import de.demmer.dennis.odrauserservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Log4j2
@RestController
@RequestMapping("/meta/tag")
public class TaggingController {

    @Autowired
    MetadataService metadataService;

    @Autowired
    UserService userService;

    @PostMapping("/star")
    public ApiResponse addStar(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TagRequest tagRequest) {
        try {
            log.info("Star request @media: " + tagRequest.getMediaId() + ", user: " + currentUser.getName());
            boolean starAdded = metadataService.addStar(new Star(currentUser.getId(), new Date(), tagRequest.getMediaId()), tagRequest.getMediaId());
            String msg = starAdded ? "star was added" : "star was removed";
            return new ApiResponse(true, "Star request at media: " + tagRequest.getMediaId() + ", " + msg );
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Star request at media: " + tagRequest.getMediaId()+ " " + e.toString());
        }
    }


    @PostMapping("/flag")
    public ApiResponse addFlag(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TagRequest tagRequest) {
        try {
            log.info("Flag request @media: " + tagRequest.getMediaId() + ", user: " + currentUser.getName());
            boolean flagAdded = metadataService.addFlag(new Flag(currentUser.getId(), new Date(), tagRequest.getMediaId()), tagRequest.getMediaId());
            String msg = flagAdded ? "flag was added" : "flag was removed";
            return new ApiResponse(true, "Flag request at media: " + tagRequest.getMediaId() + ", " + msg );
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Flag request at media: " + tagRequest.getMediaId()+ " " + e.toString());
        }
    }


    @GetMapping("/star/user/all")
    public Set<Star> getAllStars(){
        return metadataService.getAllStars();
    }


    @GetMapping("/star/user/{userId}")
    public Set<Star> getStarsByUserId(@PathVariable Long userId){
        return metadataService.getStarsByUserId(userId);
    }


}
