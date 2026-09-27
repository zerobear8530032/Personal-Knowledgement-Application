package com.example.demo.exceptions;

import com.example.demo.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundErrorsHandlers(Exception e) {
        ErrorResponse response = new ErrorResponse(false, "resource not found", e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {InvalidRoleException.class})
    public ResponseEntity<ErrorResponse> invaliRoleErrorsHandlers(Exception e){
        ErrorResponse response= new ErrorResponse(false,"Invalid Role",e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        return ResponseEntity.badRequest().body(
                ErrorResponse.unsuccessfull(
                        "Invalid value for parameter: " + ex.getName(),ex,"Invalid parameter Value "
                )
        );
    }
    @ExceptionHandler(value = {EmailAlreadyRegisteredException.class})
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyRegisteredException ex) {

        return ResponseEntity.badRequest().body(
                ErrorResponse.unsuccessfull(
                        "Email should be unique" ,ex,ex.getMessage()
                )
        );
    }
    @ExceptionHandler(AuthenticationException.class)
    public  ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
        return new ResponseEntity<>(ErrorResponse.unsuccessfull(ex.getMessage(),ex),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public  ResponseEntity<ErrorResponse> handleJwtExpiredException(ExpiredJwtException ex){
        return new ResponseEntity<>(ErrorResponse.unsuccessfull("Login again",ex),HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> unknownExceptionHandler(Exception e){
//        ErrorResponse response= new ErrorResponse(false,"Some thing rare happened",e.getMessage(), LocalDateTime.now());
//        e.printStackTrace();
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }




    }