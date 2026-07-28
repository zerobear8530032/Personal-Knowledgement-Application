package com.example.demo.exceptions;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(){

    }
}
