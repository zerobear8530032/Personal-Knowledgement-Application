package com.example.demo.configuration;

import com.example.demo.interceptor.LogginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

@ConditionalOnProperty(name = "logging.interceptor", havingValue = "true")
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    LogginInterceptor logginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logginInterceptor);//.addPathPatterns("/users/**");
    }


}
