package com.example.demo.services;

import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.UpdateUserRequest;
import com.example.demo.dtos.UserResponse;
import com.example.demo.entities.User;
import com.example.demo.exceptions.EmailAlreadyRegisteredException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.UserMapper;
import com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
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
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
