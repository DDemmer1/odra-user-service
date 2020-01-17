package de.demmer.dennis.odrauserservice.controller;

import de.demmer.dennis.odrauserservice.model.NewsColumn;
import de.demmer.dennis.odrauserservice.payload.ApiResponse;
import de.demmer.dennis.odrauserservice.repository.NewsColumnRepository;
import de.demmer.dennis.odrauserservice.security.CurrentUser;
import de.demmer.dennis.odrauserservice.security.UserPrincipal;
import de.demmer.dennis.odrauserservice.service.NewsColumnService;
import de.demmer.dennis.odrauserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserColumnsController {


    @Autowired
    UserService userService;

    @Autowired
    NewsColumnService newsColumnService;

    @GetMapping("/get/columns")
    public List<NewsColumn> getColumns(@CurrentUser UserPrincipal currentUser) {
        return userService.getColumnsByUserId(currentUser.getId());
    }


    @GetMapping("/add/column")
    public ApiResponse addColumn(@CurrentUser UserPrincipal currentUser, @RequestParam String source, @RequestParam String type, @RequestParam(required = false) String query) {
        newsColumnService.addNewsColumnToUser(currentUser.getId(), source, type, query);
        return new ApiResponse(true, "Column was added");
    }

    @GetMapping("/delete/column")
    public ApiResponse removeColumn(@CurrentUser UserPrincipal currentUser, @RequestParam long columnid) {
        try {
            newsColumnService.removeColumn(columnid, currentUser.getId());
            return new ApiResponse(true, "Column with ID '" + columnid + "' was deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "Error. Column with ID '" + columnid + "' was not deleted");
        }

    }


}
