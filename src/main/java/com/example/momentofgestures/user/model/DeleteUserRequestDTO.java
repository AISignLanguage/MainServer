package com.example.momentofgestures.user.model;

import lombok.Getter;

@Getter
public class DeleteUserRequestDTO {
    private String email;

    public DeleteUserRequestDTO(){

    }

    public DeleteUserRequestDTO(String email) {
        this.email = email;
    }
}
