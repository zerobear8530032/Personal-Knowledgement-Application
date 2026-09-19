package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication

public class PersonalKnowledgeManagementPlatform {
	public static void main(String[] args) {
		SpringApplication.run(PersonalKnowledgeManagementPlatform.class, args);
	}
}
