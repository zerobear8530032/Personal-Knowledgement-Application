package com.example.demo.services;

import com.example.demo.dtos.CustomUserDetail;
import com.example.demo.entities.User;
import com.example.demo.mappers.UserDetailsMapper;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email).orElseThrow(()-> {
            System.out.println("User not found "+email);
           return new UsernameNotFoundException("Email : " + email + " not found exception .");
        });
        CustomUserDetail userDetails=userDetailsMapper.userEntityToCustomUserDetail(user);
        return userDetails;
    }

}
