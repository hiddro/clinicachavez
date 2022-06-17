package com.peru.srv.clinicachavez.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {

    public static void main(String[] args) {

        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        System.out.println(bCryptPasswordEncoder.encode("12345"));
        System.out.println(bCryptPasswordEncoder.encode("12345").length());
    }
}
