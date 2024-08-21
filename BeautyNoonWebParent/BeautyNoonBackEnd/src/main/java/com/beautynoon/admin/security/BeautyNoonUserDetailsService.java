package com.beautynoon.admin.security;

import com.beautynoon.admin.user.UserRepository;
import com.beautynoon.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BeautyNoonUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public BeautyNoonUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("Could not find user with email:" + email);
        }
        return new BeautyNoonUserDetails(user);
    }
}
