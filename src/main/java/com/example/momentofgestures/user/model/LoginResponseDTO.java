package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private boolean success;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(boolean success) {
        this.success = success;
    }
}
