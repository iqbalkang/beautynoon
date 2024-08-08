package com.beautynoon.admin.user;

import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/check-email")
    public Boolean sd(@RequestBody User user, @RequestParam(value = "edit", required = false) Boolean edit,  @RequestParam(name = "id", required = false) Integer id) {
        return userService.isEmailUnique(user.getEmail(), edit, id);
    }
}
