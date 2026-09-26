package com.example.demo.filters;

import com.example.demo.services.CustomUserDetailsService;
import com.example.demo.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtSecurityFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");
        String token=null;
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
        }
        if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            // validate token
            if (jwtService.isValidateToken(token)) {
            // set in security context hokder
                UserDetails userDetails = jwtService.extractUserFromToken(token);
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
