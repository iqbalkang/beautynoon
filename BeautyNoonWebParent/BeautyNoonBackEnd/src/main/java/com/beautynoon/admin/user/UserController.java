package com.beautynoon.admin.user;

import com.beautynoon.admin.FileUploadUtil;
import com.beautynoon.common.entity.Role;
import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "order", required = false, defaultValue = "asc") String order,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        Page<User> page = userService.getUsersListByPage(pageNumber, sortBy, order, keyword);
        List<User> users = page.getContent();
        Integer lastPage = page.getTotalPages();

        String reverseOrder = order.equals("asc") ? "desc" : "asc";

        model.addAttribute("users", users);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("reverseOrder", reverseOrder);
        model.addAttribute("order", order);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("keyword", keyword);

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
    public String saveUser(@ModelAttribute User user,
                           RedirectAttributes redirectAttributes,
                           @RequestParam(name = "poster", required = false) MultipartFile file) throws IOException, UserNotFoundException {
        boolean isEditing = (user.getId() != null);

        if (!file.isEmpty()) {
                handleFileUpload(user, file);
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            userService.save(user);
        }

        if (isEditing) setRedirectAttributes(redirectAttributes, "success", "User has been updated successfully!");
        else setRedirectAttributes(redirectAttributes, "success", "New user added successfully!");

        String userToBeReturned = user.getEmail().split("@")[0];
        return "redirect:/users?keyword=" + userToBeReturned;
    }


    @GetMapping("/users/edit/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) throws UserNotFoundException {
            User userDB = userService.findUserById(id);
            Iterable<Role> roles = userService.getRoles();
            model.addAttribute("roles", roles);
            model.addAttribute("user", userDB);
            model.addAttribute("userId", id);
            model.addAttribute("edit", true);
            return "show-user-form";

    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id,
                             @RequestParam(name = "page", required = false, defaultValue = "1") Integer pageNumber,
                             @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
                             @RequestParam(name = "order", required = false, defaultValue = "asc") String order,
                             @RequestParam(name = "keyword", required = false) String keyword,
                             Model model,
                             RedirectAttributes redirectAttributes) throws UserNotFoundException {

            userService.deleteUser(id);
            setRedirectAttributes(redirectAttributes, "success", "User has been deleted successfully!");

            return "redirect:/users?page=" + pageNumber +"&sortBy=" + sortBy + "&order=" + order;
    }

//    Helper methods
    public void setRedirectAttributes(RedirectAttributes redirectAttributes, String status, String message) {
        redirectAttributes.addFlashAttribute("status", status);
        redirectAttributes.addFlashAttribute("message", message);
    }

    public void handleFileUpload(User user, MultipartFile file) throws IOException, UserNotFoundException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        user.setPhotos(fileName);

        User userDB = userService.save(user);
        String dirPath = "user-photos/" + userDB.getId();

        FileUploadUtil.cleanDir(dirPath);
        FileUploadUtil.saveFile(file, dirPath, fileName);
    }

    @ExceptionHandler
    public String handleUserNotFoundException(UserNotFoundException exception, RedirectAttributes redirectAttributes) {
        setRedirectAttributes(redirectAttributes, "danger", exception.getMessage());
        return "redirect:/users";
    }

}
