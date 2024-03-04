package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequestDTO {
    private String email;
    private String newPassword;

    public ChangePasswordRequestDTO() {}

    public ChangePasswordRequestDTO(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }
}
