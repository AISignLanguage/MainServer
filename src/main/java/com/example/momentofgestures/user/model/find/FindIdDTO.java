package com.example.momentofgestures.user.model.find;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdDTO {
    private String name;
    private String phone_number;

    public FindIdDTO(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }
}
