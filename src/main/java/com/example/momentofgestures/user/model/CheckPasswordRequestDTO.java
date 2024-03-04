package com.example.momentofgestures.user.model;

import lombok.Getter;

@Getter
public class CheckPasswordRequestDTO {
    private String email;


    public CheckPasswordRequestDTO() {
    }

    public CheckPasswordRequestDTO(String email) {
        this.email = email;
    }
}
