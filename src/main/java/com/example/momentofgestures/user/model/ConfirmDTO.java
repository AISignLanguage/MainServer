package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmDTO {

    private String word;

    public ConfirmDTO() {
    }

    public ConfirmDTO(String word) {
        this.word = word;
    }
}