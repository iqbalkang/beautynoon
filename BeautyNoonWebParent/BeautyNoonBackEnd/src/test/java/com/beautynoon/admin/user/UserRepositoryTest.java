package com.beautynoon.admin.user;

import com.beautynoon.common.entity.Role;
import com.beautynoon.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Test
    public void createUserWithOneRoleTest() {
        User balaUser = new User("bala", "kang", "bala@gmail.com", "zzzzz");
        Optional<Role> role = roleRepository.findById(1);

        role.ifPresent(balaUser::addRole);
        User userDB = userRepository.save(balaUser);
        Assertions.assertThat(userDB.getId()).isGreaterThan(0);
    }

    @Test
    public void createUserWithTwoRolesTest() {
        User jeetoUser = new User("jeeto", "gill", "jeeto@gmail.com", "zzzzz");
        Role editorRole = new Role(3);
        Role assistantRole = new Role(5);

        jeetoUser.addRole(editorRole);
        jeetoUser.addRole(assistantRole);

        User userDB = userRepository.save(jeetoUser);
        Assertions.assertThat(userDB.getId()).isGreaterThan(0);
    }

    @Test
    public void listAllUsersTest() {
        Iterable<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void findUserByIdTest() {
        User user = userRepository.findById(2).orElse(null);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void updateUserTest() {
        Optional<User> user = userRepository.findById(2);

        if(user.isPresent()) {
            user.get().setEnabled(true);
            User userDB = userRepository.save(user.get());
        }

        System.out.println(user);
    }

    @Test
    public void updateUserRoleTest() {
        Optional<User> user = userRepository.findById(6);

        Role editorRole = new Role(3);
        Role shipperRole = new Role(4);

        if(user.isPresent()) {
            user.get().getRoles().remove(editorRole);
            user.get().addRole(shipperRole);
            userRepository.save(user.get());
        }
    }

    @Test
    public void deleteUserByIdTest() {
        userRepository.deleteById(15);
    }

    @Test
    public void findUserByEmail() {
        User user = userRepository.findByEmail("iqbal.kang@yahoo.com");

        Assertions.assertThat(user).isNull();
    }
}