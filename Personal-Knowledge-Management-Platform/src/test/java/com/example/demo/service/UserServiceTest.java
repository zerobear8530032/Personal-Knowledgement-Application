package com.example.demo.service;

import com.example.demo.dtos.RegisterUserDTO;
import com.example.demo.dtos.UserDTO;
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
    public static void init(){
        System.out.println(" initializing objects ");
    }

//    runs after all cases test runs and run only once
    @AfterAll
    public static void cleanUp(){
        System.out.println(" clean all objects ");
    }

//    runns  before any test case starts running
    @BeforeEach
    public  void beforeTest(){
        System.out.println(" befor each  test ");
    }
//    runns  after any test case complete running
    @AfterEach
    public  void afterTest(){
        System.out.println(" after each  test ");
    }




    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;


    @Test
    public void registerUserTest1(){
//        prepration
        System.out.println("register user test1");
        RegisterUserDTO registerUserDto= new RegisterUserDTO("abc@gmail.com","123@123","zerobear");
        User user = new User(5L,registerUserDto.getName(),registerUserDto.getEmail(),registerUserDto.getPassword());
//        mocking output
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//        actual calls
        UserDTO userDto=userService.registerUser(registerUserDto);
//        assertions
        Assertions.assertEquals(user.getId(),userDto.getId());
        Assertions.assertEquals(user.getName(),userDto.getName());
        Assertions.assertEquals(user.getEmail(),userDto.getEmail());
    }

    @Test
    public void registerUserTest2(){
//        prepration
        System.out.println("register user test 2");
        RegisterUserDTO registerUserDto= new RegisterUserDTO("abc@gmail.com","123@123","zerobear");
        User user = new User(5L,registerUserDto.getName(),registerUserDto.getEmail(),registerUserDto.getPassword());
//        mocking output
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//        actual calls
        UserDTO userDto=userService.registerUser(registerUserDto);
//        assertions
        Assertions.assertEquals(user.getId(),userDto.getId());
        Assertions.assertEquals(user.getName(),userDto.getName());
        Assertions.assertEquals(user.getEmail(),userDto.getEmail());
    }

    @Test
    public void messageTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method=UserService.class.getDeclaredMethod("message", String.class);
        method.setAccessible(true);
        String param="zerobear";
        String ans= (String) method.invoke(userService,param);
        Assertions.assertEquals(param,ans);
    }


    @Test
    void deleteUserByIdTest() {
        long id = 1L;

        User user = new User(id, "zerobear", "abc@gmail.com", "hjhgjgjh");

        Mockito.when(userRepository.findById(id))
                .thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).deleteById(id);
        userService.deleteUser(id);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(user.getId());
    }@Test
    void checkDeleteUserUserNotFoundTest() {
        long id = 1L;
        Mockito.when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        UserNotFoundException ex=Assertions.assertThrows(UserNotFoundException.class,()->{
            userService.deleteUser(id);
        });

        System.out.println(ex.getMessage());
    }
}
