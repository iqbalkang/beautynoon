package com.beautynoon.admin.user;

import com.beautynoon.common.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);

    public Long countById(Integer id);
}
