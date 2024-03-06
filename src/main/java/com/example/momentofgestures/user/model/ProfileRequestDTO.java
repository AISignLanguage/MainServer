package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDTO {
    private String email;

    public ProfileRequestDTO(String email) {
        this.email = email;
    }

    public ProfileRequestDTO() {}
}
