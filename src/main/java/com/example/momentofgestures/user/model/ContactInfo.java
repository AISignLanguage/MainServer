package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfo {
    private String name;
    private String phoneNumber;
    private String uri;

    public ContactInfo() {}

    public ContactInfo(String name, String phoneNumber, String uri) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.uri = uri;
    }
}


