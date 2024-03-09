package com.example.momentofgestures.user.model;

import lombok.Getter;

@Getter
public class DeleteUserResponseDTO {
    private boolean success;

    public DeleteUserResponseDTO(){
    }

    public DeleteUserResponseDTO(boolean success) {
        this.success = success;
    }
}
