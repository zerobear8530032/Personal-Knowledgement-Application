package com.example.demo.controllers;

import com.example.demo.dtos.RegisterUserDTO;
import com.example.demo.dtos.UpdateUserDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.enums.UserEnum;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    @Value("${users.max-pages.size}")
    private int MAXPageSize;


    private final UserService userService;

    @Autowired
    public  UserController(UserService userService){
        this.userService =userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers(@RequestParam(name="page",required = false ,defaultValue = "1") int page, @RequestParam(name="size",required = false,defaultValue ="5") int size, @RequestParam(name="sortBy",required = false,defaultValue = "ID") UserEnum sortBy, @RequestParam(name="direction",required = false,defaultValue = "DESC")Sort.Direction direction){

        size= Math.min(MAXPageSize,size);
        Sort sort=Sort.by(direction,sortBy.getValue());
        PageRequest pageRequest= PageRequest.of(page,size,sort);
        List<UserDTO> users=userService.getAllUsers(pageRequest);
        return ResponseEntity.ok(ApiResponse.success("Fetch all users successfully",users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable(name = "id") Long id){
        UserDTO user=userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success("Fetch user by ID successfully",user));

    }

    @PostMapping
    public   ResponseEntity<ApiResponse<UserDTO>>  createUser(@RequestBody RegisterUserDTO registerUser){
        UserDTO user=userService.registerUser(registerUser);
        return new ResponseEntity<ApiResponse<UserDTO>>(ApiResponse.success("Create new User successfully",user), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public   ResponseEntity<ApiResponse<UserDTO>>  updateUser(@RequestBody UpdateUserDTO updatedUser, @PathVariable(name = "id") Long id){
        UserDTO user=userService.updateUser(id,updatedUser);
        return new ResponseEntity<ApiResponse<UserDTO>>(ApiResponse.success("Update user successfully",user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> deleteUser(@PathVariable(name="id") Long id ){
        userService.deleteUser(id);
        return new ResponseEntity<ApiResponse<UserDTO>>(ApiResponse.success("Deleted User sucessfully",null), HttpStatus.OK);
    }
}
