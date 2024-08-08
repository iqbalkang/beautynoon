package com.beautynoon.admin.user;

import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chk")
public class UserRestController {
    private UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/check")
    public Boolean checkEmail(@RequestBody User user) {
        System.out.println(user);
        System.out.println("check");
        User userDB = userRepository.findByEmail(user.getEmail());
        System.out.println(userDB);
        return userDB == null;
    }
}
