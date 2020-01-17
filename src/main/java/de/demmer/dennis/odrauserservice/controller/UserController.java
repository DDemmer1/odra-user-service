package de.demmer.dennis.odrauserservice.controller;

import de.demmer.dennis.odrauserservice.model.User;
import de.demmer.dennis.odrauserservice.payload.ApiResponse;
import de.demmer.dennis.odrauserservice.security.CurrentUser;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import de.demmer.dennis.odrauserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/all")
    public List<User> getAllUsers(@CurrentUser UserPrincipal currentUser) {
        return userService.getAllUsers();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/delete/{id}")
    public ApiResponse deleteUser(@CurrentUser UserPrincipal currentUser, @PathVariable long id) {
        if (currentUser.getId() == id) {
            return new ApiResponse(false, "You cannot delete yourself");
        }
        userService.deleteUser(id);
        return new ApiResponse(true, "User with ID: " + id + " is deleted");
    }


}
