package com.beautynoon.admin.user;

import com.beautynoon.common.entity.Role;
import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        Iterable<User> users = userService.getUsers();
//        List<User> users = List.of(new User());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/new")
    public String showUserForm(Model model) {
        Iterable<Role> roles = userService.getRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "show-user-form";
    }

    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        Boolean isEditing = (user.getId() != null);

        try {
            userService.save(user);
            if(isEditing) setRedirectAttributes(redirectAttributes, "success", "User has been updated successfully!");
            else setRedirectAttributes(redirectAttributes, "success", "New user added successfully!");
        } catch (UserNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, "danger", exception.getMessage());
        }

            return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User userDB = userService.findUserById(id);
            Iterable<Role> roles = userService.getRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("user", userDB);
            model.addAttribute("edit", true);
            return "show-user-form";
        } catch (UserNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, "danger", exception.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            setRedirectAttributes(redirectAttributes, "success", "User has been deleted successfully!");
        } catch (UserNotFoundException exception) {
            setRedirectAttributes(redirectAttributes, "danger", exception.getMessage());
        }
            return "redirect:/users";
    }

    public void setRedirectAttributes(RedirectAttributes redirectAttributes, String status, String message) {
        redirectAttributes.addFlashAttribute("status", status);
        redirectAttributes.addFlashAttribute("message", message);
    }

}
