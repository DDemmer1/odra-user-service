package de.demmer.dennis.odrauserservice.repository;

import de.demmer.dennis.odrauserservice.model.NewsColumn;
import de.demmer.dennis.odrauserservice.model.User;
import org.springframework.data.repository.CrudRepository;

public interface NewsColumnRepository extends CrudRepository<NewsColumn, Long> {

    void deleteByIdAndUser(Long id, User user);

}
