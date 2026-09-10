package com.example.demo.exceptions;

public class FolderDoesNotExistException extends  NotFoundException {
    public FolderDoesNotExistException(String msg) {
        super(msg);
    }
    public FolderDoesNotExistException(){}
}
