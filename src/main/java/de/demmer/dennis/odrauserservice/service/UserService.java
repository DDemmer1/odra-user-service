package de.demmer.dennis.odrauserservice.service;

import de.demmer.dennis.odrauserservice.model.NewsColumn;
import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.repository.ColumnRepository;
import de.demmer.dennis.odrauserservice.repository.UserRepository;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;


    public List<NewsColumn> getColumnsByUserId(long id){
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return new ArrayList<>();
        }
        return user.getNewsColumnList();
    }



    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName).get();
    }
}
