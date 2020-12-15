package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.meta.Answer;
import de.demmer.dennis.odrauserservice.model.meta.Comment;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
