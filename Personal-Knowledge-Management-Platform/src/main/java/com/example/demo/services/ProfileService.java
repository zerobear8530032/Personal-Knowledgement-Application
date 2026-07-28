package com.example.demo.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class ProfileService {
    @Value("${profile}")
    String profile;
    @PostConstruct
    public void init(){
        System.out.println("Using profile :"+profile);
    }
}
