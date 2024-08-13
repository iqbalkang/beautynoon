package com.beautynoon.admin.user;

import com.beautynoon.common.entity.Role;
import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/check-email")
    public Boolean isEmailUniquex(@RequestBody User user,  @RequestParam(name = "id", required = false) Integer id) {
        System.out.println(id);
        return userService.isEmailUnique(user.getEmail(), id);
    }
//
//    @GetMapping("/users/new")
//    public Map<String, Object> showUserForm(Model model) {
////        Iterable<Role> roles = userService.getRoles();
////        User user = new User();
////        return roles;
//
//        Iterable<Role> roles = userService.getRoles();
//        User user = new User("bala", "kang", "bala@gmail.com", "zzzzz");
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("user", user);
//        response.put("roles", roles);
//
//        return response;
//    }
}
