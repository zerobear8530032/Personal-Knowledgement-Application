package com.example.demo.enums;
public enum NotesEnum {
    ID("id"),
    TITLE("title"),
    CONTENT("content"),
    CREATEDAT("createdAt"),
    UPDATEDAT("updatedAt");

    String value;
    NotesEnum(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
};
