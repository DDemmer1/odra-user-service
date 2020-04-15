package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.meta.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    void deleteById(long id);

    List<Topic> findAllByTopicName(String topicName);
}
