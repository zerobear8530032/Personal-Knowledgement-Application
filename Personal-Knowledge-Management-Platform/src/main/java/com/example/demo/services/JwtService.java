package com.example.demo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    String SECRET ="Vag2bXzW3GddViTrx4kUoviWzslZ2Zm0W3mnqwsokjA";
    private final SecretKey secretKey= Keys.hmacShaKeyFor(SECRET.getBytes());


    private  final  CustomUserDetailsService userDetailsService;

    public JwtService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String email, Long id){
        String token =Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .claim("id",id)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(secretKey,SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public  String extractEmailFromToken(String token){
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }
    public  Long extractIdFromToken(String token){
        Claims claims = extractClaims(token);
        return claims.get("id",Long.class);
    }
    public  Date extractExpirationDateFromToken(String token){
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    public Claims extractClaims(String token) {
        Claims claims= Jwts.parser()
                  .verifyWith(secretKey)
                  .build()
                  .parseSignedClaims(token)
                  .getPayload();
        return claims;
    }

    public  boolean isTokenExpired(String token){
        Date expDate= extractExpirationDateFromToken(token);
        return expDate.before(new Date());
    }
    public boolean isValidateToken(String token) {
        String email = extractEmailFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return userDetails.getUsername().equalsIgnoreCase(email) && !isTokenExpired(token);
    }

    public UserDetails extractUserFromToken(String token){
        String email = extractEmailFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return userDetails;
    }
}
