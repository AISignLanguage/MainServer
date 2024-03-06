package com.example.momentofgestures.user.model.find;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPwdDTO {
    private String name;
    private String email;

    public FindPwdDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
