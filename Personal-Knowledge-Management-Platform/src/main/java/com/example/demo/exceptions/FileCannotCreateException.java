package com.example.demo.exceptions;

public class FileCannotCreateException extends RuntimeException {
    public FileCannotCreateException(String s) {
    }
    public FileCannotCreateException(){
        super();
    }
}
