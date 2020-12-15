package de.demmer.dennis.odrauserservice.controller.metadata;

import com.google.gson.Gson;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.model.meta.Answer;
import de.demmer.dennis.odrauserservice.payload.AnswerRequest;
import de.demmer.dennis.odrauserservice.payload.ApiResponse;
import de.demmer.dennis.odrauserservice.payload.CommentRequest;
import de.demmer.dennis.odrauserservice.repository.AnswerRepository;
import de.demmer.dennis.odrauserservice.repository.CommentRepository;
import de.demmer.dennis.odrauserservice.security.CurrentUser;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import de.demmer.dennis.odrauserservice.service.CommentService;
import de.demmer.dennis.odrauserservice.service.MetadataService;
import de.demmer.dennis.odrauserservice.service.SseService;
import de.demmer.dennis.odrauserservice.service.UserService;
import de.demmer.dennis.odrauserservice.service.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/meta")
public class CommentSystemController {

    @Autowired
    SseService sseService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    JsonConverter jsonConverter;

    @Autowired
    MetadataService metadataService;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("/comment/add")
    public ApiResponse addComment(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody CommentRequest commentRequest){
        System.out.println("Adding Comment");
        User user = userService.getUserById(currentUser.getId());
        commentService.addComment(user, commentRequest);
        sseService.emmitMetadataUpdate(commentRequest.getMediaId());
        return new ApiResponse(true,"Comment successfully added");
    }

    @PostMapping("/answer/add")
    public ApiResponse addAnswer(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody AnswerRequest answerRequest){
        System.out.println(jsonConverter.convert(answerRequest));

        System.out.println("Adding Answer");
        User user = userService.getUserById(currentUser.getId());
        commentService.addAnswer(user,answerRequest);
        sseService.emmitMetadataUpdate(answerRequest.getMediaId());
        return new ApiResponse(true,"Answer successfully added");
    }


    @GetMapping("/comment/delete")
    public ApiResponse deleteComment(@CurrentUser UserPrincipal currentUser, @RequestParam Long id, @RequestParam Long metaId ){
        System.out.println("Delete commment with id: " + id);
        metadataService.deleteComment(id,metaId);
        return new ApiResponse(true,"Comment successfully deleted");

    }


    @GetMapping("/answer/delete")
    public ApiResponse deleteAnswer(@CurrentUser UserPrincipal currentUser, @RequestParam Long id, @RequestParam Long metaId ){
        System.out.println("Delete commment with id: " + id);
        Answer answer= answerRepository.findById(id).orElse(null);
        if(answer != null){
            answer.setText("This message was deleted");
        }
        answerRepository.save(answer);
        return new ApiResponse(true,"Comment successfully deleted");

    }




    }

