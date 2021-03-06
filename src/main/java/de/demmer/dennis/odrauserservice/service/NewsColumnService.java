package de.demmer.dennis.odrauserservice.service;

import de.demmer.dennis.odrauserservice.model.NewsColumn;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NewsColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private UserService userService;

    public NewsColumn getColumnByID(Long id){
        return columnRepository.findById(id).orElse(null);

    }

    public void addColumnToUser(Long userId, String source, String type, String query) {

        User user = userService.getUserById(userId);
        NewsColumn column = new NewsColumn();
        column.setSource(source);
        column.setType(type);
        column.setUser(user);
        if (query != null) {
            column.setQuery(query);
        }
        columnRepository.save(column);

        user.getNewsColumnList().add(column);
        userService.save(user);


    }

    public void removeColumn(Long columnID, Long userId) {
        User user = userService.getUserById(userId);
        NewsColumn column = getColumnByID(columnID);
        user.getNewsColumnList().remove(column);
        userService.save(user);
        columnRepository.deleteByIdAndUser(columnID, userService.getUserById(userId));
    }
}
