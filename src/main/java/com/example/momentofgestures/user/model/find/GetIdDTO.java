package com.example.momentofgestures.user.model.find;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetIdDTO {
    private String email;

    public GetIdDTO(String email) {
        this.email = email;
    }
}
