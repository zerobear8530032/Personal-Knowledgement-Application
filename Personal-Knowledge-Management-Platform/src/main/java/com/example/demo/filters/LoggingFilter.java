package com.example.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@ConditionalOnProperty(
        name = "logging.filter",
        havingValue = "true"
)
public class LoggingFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("Logging request using filter:"+request.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
//        response.setStatus(401);
        System.out.println("Logging request using filter:"+request.getRequestURI());
    }

    @Override
    public void destroy() {
    }
}
