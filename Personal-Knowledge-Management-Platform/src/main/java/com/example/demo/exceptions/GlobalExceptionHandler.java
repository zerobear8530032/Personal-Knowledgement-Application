package com.example.demo.exceptions;

import com.example.demo.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoteNotFoundException.class, UserNotFoundException.class,FolderDoesNotExistException.class})
    public ResponseEntity<ErrorResponse> notFoundErrorsHandlers(Exception e){
        ErrorResponse response= new ErrorResponse(false,"resource not found",e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> unknownExceptionHandler(Exception e){
//        ErrorResponse response= new ErrorResponse(false,"Some thing rare happened",e.getMessage(), LocalDateTime.now());
//        e.printStackTrace();
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }




    }