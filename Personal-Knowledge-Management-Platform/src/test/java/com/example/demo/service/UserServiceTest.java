package com.example.demo.service;

import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.UserResponse;
import com.example.demo.entities.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    //    runs before any test runs and run only once
    @BeforeAll
    public static void init() {
        System.out.println(" initializing objects ");
    }

    //    runs after all cases test runs and run only once
    @AfterAll
    public static void cleanUp() {
        System.out.println(" clean all objects ");
    }

    //    runns  before any test case starts running
    @BeforeEach
    public void beforeTest() {
        System.out.println(" befor each  test ");
    }

    //    runns  after any test case complete running
    @AfterEach
    public void afterTest() {
        System.out.println(" after each  test ");
    }


    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;


    @Test
    public void registerUserTest1() {
    }
}
