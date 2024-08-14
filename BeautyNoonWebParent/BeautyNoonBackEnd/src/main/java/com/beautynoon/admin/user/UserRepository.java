package com.beautynoon.admin.user;

import com.beautynoon.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {
    public User findByEmail(String email);

    public Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName, ' ', u.email) LIKE %?1% ")
    public Page<User> findAll(@Param("keyword") String keyword, Pageable pageable);

}
