package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDTO {
    private String name;
    private String phoneNumber;
    private String profileImageUrl;

    public PhoneDTO() {}

    public PhoneDTO(String name, String phoneNumber, String uri) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = uri;
    }
}


