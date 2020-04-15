package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.meta.tag.Star;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StarRepository extends CrudRepository<Star, Long> {

    void deleteByUseridAndMediaId(Long userId, Long mediaId);

    List<Star> findAllByUserid(long userId);
}
