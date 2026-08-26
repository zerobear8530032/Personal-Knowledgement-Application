package com.example.demo.enums;

public enum AttachmentEnum {

    ID("id"),
    FILENAME("originalName");

    String value;
    AttachmentEnum(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
