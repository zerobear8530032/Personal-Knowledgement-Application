package com.example.demo.services;

import com.example.demo.dtos.NoteDTO;
import com.example.demo.dtos.RegisterUserDTO;
import com.example.demo.dtos.UpdateUserDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.entities.Note;
import com.example.demo.entities.User;
import com.example.demo.exceptions.NoteNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@ToString
@NoArgsConstructor
public class UserService {
    private UserRepository userRepository;
    @Autowired

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    @Transactional
    public UserDTO registerUser(RegisterUserDTO registerUser){
        User user= registerUser.toEntity();
        User savedUser=userRepository.save(user);
        return UserDTO.toDTO(savedUser);
    }
    @Transactional
    public UserDTO getUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(" User ID "+id+" Not Found"));
        return UserDTO.toDTO(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers(PageRequest pageRequest){
        final UserDTO mapper= new UserDTO();
        List<UserDTO> users= userRepository.findAll(pageRequest).stream().map(user -> mapper.toDTO(user)).toList();
        return  users;
    }
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO){
        User user= userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User ID "+id+" not present in the Database"));
        user.setName(updateUserDTO.getName());
        user.setEmail(updateUserDTO.getEmail());
        userRepository.save(user);
        return UserDTO.toDTO(user);
    }
    @Transactional
    public void deleteUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(" User ID "+id+" Not Found"));
        userRepository.deleteById(id);
    }

    private String message(String name){
        return name+" "+name;
    }
}
