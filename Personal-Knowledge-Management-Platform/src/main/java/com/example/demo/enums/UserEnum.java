package com.example.demo.enums;

public enum UserEnum {
        ID("id"),
        NAME("name"),
        EMAIL("email");

        String value;
        UserEnum(String value) {
            this.value=value;
        }

        public String getValue() {
            return value;
        }

};
