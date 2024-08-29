package com.beautynoon.admin.user;

import com.beautynoon.admin.security.BeautyNoonUserDetails;
import com.beautynoon.common.entity.Role;
import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {

    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String updateAccount(@AuthenticationPrincipal BeautyNoonUserDetails loggedInUser, Model model) {
        String email = loggedInUser.getUsername();
        User user = userService.getUser(email);
        Iterable<Role> roles = userService.getRoles();
//        System.out.println(user);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("edit", true);
        model.addAttribute("disableCheckbox", true);
        return "show-user-form";
    }

//    @GetMapping("/users/edit/{id}")
//    public String updateUserForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) throws UserNotFoundException {
//        User userDB = userService.findUserById(id);
//        Iterable<Role> roles = userService.getRoles();
//        model.addAttribute("roles", roles);
//        model.addAttribute("user", userDB);
//        model.addAttribute("userId", id);
//        model.addAttribute("edit", true);
//        return "show-user-form";
//    }


}
