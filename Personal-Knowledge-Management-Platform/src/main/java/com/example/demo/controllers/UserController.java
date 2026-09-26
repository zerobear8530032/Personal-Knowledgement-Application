package com.example.demo.controllers;

import com.example.demo.Utility.Utility;
import com.example.demo.dtos.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    @Value("${users.max-pages.size}")
    private int MAXPageSize;


    private final UserService userService;

    private  final Utility utility;

    @Autowired
    public  UserController(UserService userService, Utility utility){
        this.userService =userService;

        this.utility = utility;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(@RequestParam(name="page",required = false ,defaultValue = "0") int page, @RequestParam(name="size",required = false,defaultValue ="5") int size, @RequestParam(name="sortBy",required = false,defaultValue = "ID") UserEnum sortBy, @RequestParam(name="direction",required = false,defaultValue = "DESC")Sort.Direction direction){
        PageRequest pageRequest=utility.getPagination(size,page,sortBy,direction);
        Page<UserResponse> users=userService.getAllUsers(pageRequest);
        PageResponse<UserResponse> response = new PageResponse<>(
                users.getContent(),
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("Fetch all users successfully",response));
    }


    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<JwtResponse>> loginUser(@RequestBody LoginRequest loginRequest){
        JwtResponse jwt = userService.loginUser(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("generated JWT token",jwt));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable(name = "id") Long id){
        UserResponse user=userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success("Fetch user by ID successfully",user));
    }

    @PostMapping("/auth/register")
    public   ResponseEntity<ApiResponse<UserResponse>>  createUser(@RequestBody RegisterUserRequest registerUser){
        UserResponse user=userService.registerUser(registerUser);
        return new ResponseEntity<ApiResponse<UserResponse>>(ApiResponse.success("Create new User successfully",user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserResponse>>  updateUser(@RequestBody UpdateUserRequest updatedUser){
        Long id =utility.getCurrentLoggedInUser().getId();
        UserResponse user=userService.updateUser(id,updatedUser);
        return new ResponseEntity<ApiResponse<UserResponse>>(ApiResponse.success("Update user successfully",user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUser(@PathVariable(name="id") Long id ){
        userService.deleteUser(id);
        return new ResponseEntity<ApiResponse<UserResponse>>(ApiResponse.success("Deleted User sucessfully",null), HttpStatus.OK);
    }
}
