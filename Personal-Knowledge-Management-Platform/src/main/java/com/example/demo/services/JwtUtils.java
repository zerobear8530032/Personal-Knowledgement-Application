package com.example.demo.services;

import com.example.demo.mappers.UserDetailsMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
public final UserDetailsService userDetailsService;

    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public JwtUtils(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String email){
        String token =Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(key)
                .compact();
        return token;
    }


    public Claims extractClaims(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public boolean validateToken(String token){
        try {
            Claims claims = extractClaims(token);

            String username = claims.getSubject();
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            return username.equals(userDetails.getUsername())
                    && !claims.getExpiration().before(new Date());

        } catch (Exception e) {
            return false;
        }

    }


}
