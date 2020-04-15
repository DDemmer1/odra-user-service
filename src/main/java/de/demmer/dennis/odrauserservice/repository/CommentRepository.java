package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.meta.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
