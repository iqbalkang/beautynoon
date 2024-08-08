package com.beautynoon.admin.user;

import com.beautynoon.common.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class RoleRepositoryTest {

    private RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    public void createRoleTest() {
        Role adminRole = new Role("admin", "Manage everything");
        Role roleDB = roleRepository.save(adminRole);

        Assertions.assertThat(roleDB.getId()).isGreaterThan(0);
    }

    @Test
    public void createRolesTest() {
        Role salesPersonRole = new Role("salesperson", "Manage product price, customers, shipping, orders and sales report");
        Role editorRole = new Role("editor", "Manage categories, brands, products, articles and menus");
        Role shaipperRole = new Role("shipper", "View products, orders and update order status");
        Role assistantRole = new Role("assistant", "Manage questions and reviews");

        Iterable<Role> roles = roleRepository.saveAll(List.of(salesPersonRole, editorRole, shaipperRole, assistantRole));

        Assertions.assertThat(roles).isNotEmpty();
        Assertions.assertThat(roles).hasSize(4);
        Assertions.assertThat(roles).allMatch(role -> role.getId() > 0);
    }

}