package com.example.momentofgestures.user.model;

public class CheckPasswordResponseDTO {
    private String password;

    public CheckPasswordResponseDTO() {
    }

    public CheckPasswordResponseDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
