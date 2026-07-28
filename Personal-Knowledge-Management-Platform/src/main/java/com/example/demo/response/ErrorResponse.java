package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public  static  ErrorResponse unsuccessfull(String error,Exception e){
        return new ErrorResponse(false,error,e.getMessage(),LocalDateTime.now());
    }
    public  static  ErrorResponse unsuccessfull(String error,Exception e,String msg){
        return new ErrorResponse(false,error,msg,LocalDateTime.now());
    }
}