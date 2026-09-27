package com.example.demo.services;

import com.example.demo.dtos.*;
import com.example.demo.entities.User;
import com.example.demo.enums.Permissions;
import com.example.demo.enums.Role;
import com.example.demo.exceptions.EmailAlreadyRegisteredException;
import com.example.demo.exceptions.InvalidRoleException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtUtils){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    @Transactional
    public UserResponse registerUser(RegisterUserRequest registerUser){
        String email = registerUser.getEmail();
        Optional<User> emailUser= userRepository.findByEmail(email);
        if(emailUser.isPresent()){
            throw new EmailAlreadyRegisteredException("Email : "+email+" Already registered");
        }
        registerUser.setPassword(encryptPassword(registerUser.getPassword()));
        User user= userMapper.registerUserToEntity(registerUser);
        user.setRole(Role.USER);
        User savedUser=userRepository.save(user);
        return userMapper.userEntityToUserResponse(savedUser);
    }

    @Transactional
    public UserResponse getUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(" User ID "+id+" Not Found"));
        return userMapper.userEntityToUserResponse(user);
    }

    @Transactional
    public Page<UserResponse> getAllUsers(PageRequest pageRequest){
        Page<UserResponse> users= userRepository.findAll(pageRequest).map(user -> userMapper.userEntityToUserResponse(user));
        return  users;
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest){
        User user= userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User ID "+id+" not present in the Database"));
        user.setName(updateUserRequest.getName());
        String email = updateUserRequest.getEmail();
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent() && !existing.get().getId().equals(user.getId())) {
            throw new EmailAlreadyRegisteredException("Email : "+email+" Already registered");
        }
        user.setEmail(email);
        userRepository.save(user);
        return userMapper.userEntityToUserResponse(user);
    }
    @Transactional
    public void deleteUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(" User ID "+id+" Not Found"));
        userRepository.deleteById(id);
    }

    private String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }

    public JwtResponse loginUser(LoginRequest loginRequest){
        String userEmail= loginRequest.getEmail();
        String userPassword= loginRequest.getPassword();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail,userPassword)
            );
        }catch (AuthenticationException e){

            throw e;
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException("User Email "+userEmail+ " not found."));

        String jwtToken = jwtUtils.generateToken(userEmail,user.getId());
        return new JwtResponse( jwtToken);
    }



    @Transactional
    public UserResponse createUser(CreateUserRequest createUserRequest){
        String email = createUserRequest.getEmail();
        Optional<User> emailUser= userRepository.findByEmail(email);
        if(emailUser.isPresent()){
            throw new EmailAlreadyRegisteredException("Email : "+email+" Already registered");
        }
        createUserRequest.setPassword(encryptPassword(createUserRequest.getPassword()));
        User user= userMapper.createUserToEntity(createUserRequest);
        User savedUser=userRepository.save(user);
        return userMapper.userEntityToUserResponse(savedUser);
    }


    public UserResponse updateUserRole(UpdateRoleRequest updateRoleRequest) {
        User user= userRepository.findById(updateRoleRequest.getId()).orElseThrow(()-> new UserNotFoundException("User Id "+updateRoleRequest.getId()));
        try{
            user.setRole(Role.valueOf(updateRoleRequest.getRole()));
        }catch (Exception e){
            throw  new InvalidRoleException(updateRoleRequest.getRole()+"  this role does not exists");
        }
        User savedUser= userRepository.save(user);
        return  userMapper.userEntityToUserResponse(savedUser);
    }
}
