package com.example.demo.exceptions;

public class NoteNotFoundException extends NotFoundException {
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(){

    }
}
