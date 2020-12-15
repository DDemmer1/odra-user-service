package de.demmer.dennis.odrauserservice.service;

import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.model.meta.Answer;
import de.demmer.dennis.odrauserservice.model.meta.Comment;
import de.demmer.dennis.odrauserservice.model.meta.Metadata;
import de.demmer.dennis.odrauserservice.payload.AnswerRequest;
import de.demmer.dennis.odrauserservice.payload.CommentRequest;
import de.demmer.dennis.odrauserservice.repository.AnswerRepository;
import de.demmer.dennis.odrauserservice.repository.CommentRepository;
import de.demmer.dennis.odrauserservice.repository.MetadataRepository;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import de.demmer.dennis.odrauserservice.service.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MetadataRepository metadataRepository;


    public boolean addComment(User user, CommentRequest request) {
        try {
            Comment comment = requestToComment(request, user);
            commentRepository.save(comment);

            Metadata metadata = metadataRepository.findByMediaId(request.getMediaId()).orElse(new Metadata(request.getMediaId()));
            metadata.getComments().add(comment);
            metadataRepository.save(metadata);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addAnswer(User user, AnswerRequest request) {
        try {
            Answer answer = requestToAnswer(request, user);

            Metadata metadata = metadataRepository.findByMediaId(request.getMediaId()).orElse(new Metadata(request.getMediaId()));
            Comment commentToAnswer = null;
            for (Comment comment : metadata.getComments()) {
                if (comment.getId() == request.getCommentId()) {
                    commentToAnswer = comment;
                }
            }

//            Comment commentToAnswer = metadata.getComments().stream()
//                    .filter(comment -> comment.getId() == request.getCommentId())
//                    .findAny()
//                    .orElse(null);

            commentToAnswer.getAnswers().add(answer);
            answerRepository.save(answer);
            commentRepository.save(commentToAnswer);
            metadataRepository.save(metadata);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    private Comment requestToComment(CommentRequest request, User user) {
        Comment comment = new Comment();
        comment.setColor(request.getColor());
        comment.setEnd(request.getEnd());
        comment.setStart(request.getStart());
        comment.setCommentText(request.getCommentText());
        comment.setUserId(user.getId());
        comment.setTimestamp(new Date());
        comment.setSelectedText(request.getSelectedText());
        comment.setMediaId(request.getMediaId());

        return comment;
    }


    private Answer requestToAnswer(AnswerRequest request, User user) {
        Answer answer = new Answer();
        answer.setMediaId(request.getMediaId());
        answer.setText(request.getText());
        answer.setTimestamp(new Date());
        answer.setUserId(user.getId());
        return answer;
    }


}
