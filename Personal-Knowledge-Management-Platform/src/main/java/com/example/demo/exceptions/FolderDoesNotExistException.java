package com.example.demo.exceptions;

public class FolderDoesNotExistException extends  RuntimeException {
    public FolderDoesNotExistException(String msg) {
        super(msg);
    }
    public FolderDoesNotExistException(){}
}
