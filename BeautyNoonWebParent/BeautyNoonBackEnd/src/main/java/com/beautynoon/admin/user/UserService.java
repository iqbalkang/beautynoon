package com.beautynoon.admin.user;

import com.beautynoon.common.entity.Role;
import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public UserRepository userRepository;
    public RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> getUsers() {
//        return userRepository.findAll();
        Integer pageNumber = 1;
        Integer pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);
        System.out.println(page.getContent());
        return page.getContent();
    }

    public User save(User user) throws UserNotFoundException {
        Boolean isEditing = (user.getId() != null);

        if(isEditing) handleEditingUser(user);
        else encodePassword(user);

        return userRepository.save(user);
    }

    private void handleEditingUser(User user) throws UserNotFoundException {
        boolean isPasswordChanged = !user.getPassword().isEmpty();
        User userDB = findUserById(user.getId());

        if(!isPasswordChanged) user.setPassword(userDB.getPassword());
        else encodePassword(user);
    }


    public Iterable<Role> getRoles() {
        return roleRepository.findAll();
    }

    public User findUserById(Integer id) throws UserNotFoundException {
        Optional<User> userDB = userRepository.findById(id);
        return userDB.orElseThrow(() -> new UserNotFoundException("No user found with id " + id));
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long count = userRepository.countById(id);

        if(count == null || count == 0) throw new UserNotFoundException("No user found with id " + id);
        else userRepository.deleteById(id);

    }


    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public Boolean isEmailUnique(String email, Boolean edit, Integer id) {

        User user = userRepository.findByEmail(email);
        if(user == null) return true;

        boolean isCreatingNew = ( id == null );

        if(isCreatingNew && user != null) return false;

        return user.getId() == id;
    }

}
