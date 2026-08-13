package com.example.demo.controllers;

import com.example.demo.dtos.RegisterUserRequest;
import com.example.demo.dtos.UpdateUserRequest;
import com.example.demo.dtos.UserResponse;
import com.example.demo.enums.UserEnum;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(@RequestParam(name="page",required = false ,defaultValue = "0") int page, @RequestParam(name="size",required = false,defaultValue ="5") int size, @RequestParam(name="sortBy",required = false,defaultValue = "ID") UserEnum sortBy, @RequestParam(name="direction",required = false,defaultValue = "DESC")Sort.Direction direction){
        if(size>0){
            size= Math.min(MAXPageSize,size);
        }else{
            size=Math.max(size,5);
        }
        if(page<0){
            page=0;
        }
        Sort sort=Sort.by(direction,sortBy.getValue());
        PageRequest pageRequest= PageRequest.of(page,size,sort);
        Page<UserResponse> users=userService.getAllUsers(pageRequest);
        return ResponseEntity.ok(ApiResponse.success("Fetch all users successfully",users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable(name = "id") Long id){
        UserResponse user=userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success("Fetch user by ID successfully",user));
    }

    @PostMapping
    public   ResponseEntity<ApiResponse<UserResponse>>  createUser(@RequestBody RegisterUserRequest registerUser){
        UserResponse user=userService.registerUser(registerUser);
        return new ResponseEntity<ApiResponse<UserResponse>>(ApiResponse.success("Create new User successfully",user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public   ResponseEntity<ApiResponse<UserResponse>>  updateUser(@RequestBody UpdateUserRequest updatedUser, @PathVariable(name = "id") Long id){
        UserResponse user=userService.updateUser(id,updatedUser);
        return new ResponseEntity<ApiResponse<UserResponse>>(ApiResponse.success("Update user successfully",user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable(name="id") Long id ){
        userService.deleteUser(id);
        return new ResponseEntity<ApiResponse<UserResponse>>(ApiResponse.success("Deleted User sucessfully",null), HttpStatus.OK);
    }
}
