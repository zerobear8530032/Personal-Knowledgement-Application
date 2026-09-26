package com.example.demo.configuration;

import com.example.demo.filters.JwtSecurityFilter;
import com.example.demo.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity

public class JwtSecurityConfig {

    private final JwtSecurityFilter jwtSecurityFilter;

    public JwtSecurityConfig(JwtSecurityFilter jwtSecurityFilter) {
        this.jwtSecurityFilter = jwtSecurityFilter;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http){
        http.csrf(crfs->crfs.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/users/auth/**").permitAll()
                                .requestMatchers("/crons/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated());
        http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager daoAuthenticationProvider(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}
