package de.demmer.dennis.odrauserservice.service;

import de.demmer.dennis.odrauserservice.model.NewsColumn;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.repository.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialMediaColumnService {

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private UserService userService;

    private String normalize(String input){
        String output = input.replaceAll(" ","").replaceAll("#","");
        return output;
    }

    public void addColumnToUser(Long userId, String source, String type, String query) {
        User user = userService.getUserById(userId);
        NewsColumn column = new NewsColumn();
        column.setSource(source);
        column.setType(type);
        column.setUser(user);
        query = normalize(query);
        if (query != null) {
            column.setQuery(query);
        }
        columnRepository.save(column);
        user.getNewsColumnList().add(column);
        userService.save(user);

    }

}
