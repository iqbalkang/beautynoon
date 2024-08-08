package com.beautynoon.admin.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void encodePasswordTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("zzzzz");
        System.out.println(encodedPassword);
        String rawPassword = "zzzzz";

        boolean match = bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
        Assertions.assertTrue(match);
    }
}
