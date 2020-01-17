package de.demmer.dennis.odrauserservice.service;

import de.demmer.dennis.odrauserservice.model.NewsColumn;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.repository.NewsColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NewsColumnService {

    @Autowired
    private NewsColumnRepository newsColumnRepository;

    @Autowired
    private UserService userService;

    public NewsColumn getColumnByID(Long id){
        return newsColumnRepository.findById(id).orElse(null);

    }

    public void addNewsColumnToUser(Long userId, String source, String type, String query) {
        //TODO add query/type handling
        User user = userService.getUserById(userId);
        NewsColumn column = new NewsColumn();
        column.setSource(source);
        column.setType(type);
        column.setUser(user);
        if (query != null) {
            column.setQuery(query);
        }
        newsColumnRepository.save(column);

        user.getNewsColumnList().add(column);
        userService.save(user);


    }

    public void removeColumn(Long columnID, Long userId) {
        User user = userService.getUserById(userId);
        NewsColumn column = getColumnByID(columnID);
        user.getNewsColumnList().remove(column);
        userService.save(user);
        newsColumnRepository.deleteByIdAndUser(columnID, userService.getUserById(userId));
    }
}
