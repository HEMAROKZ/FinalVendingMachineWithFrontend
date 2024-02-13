//package com.VendingMachine.security;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Value("${spring.security.user.name}")
//    private  String username;
//
//    @Value("${spring.security.user.password}")
//    private String encodedPassword;
//
//    public CustomUserDetailsService() {
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String inputUsername) throws UsernameNotFoundException {
//        if (!username.equals(inputUsername)) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return User.builder()
//                .username(username)
//                .password(encodedPassword)
//                .roles("USER")
//                .build();
//    }
//}