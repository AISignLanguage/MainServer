package com.example.momentofgestures.user.model;

import lombok.Getter;

@Getter
public class ChangePasswordResponseDTO {
    private boolean success;

    public ChangePasswordResponseDTO() {}

    public ChangePasswordResponseDTO(boolean success) {
        this.success = success;
    }
}
