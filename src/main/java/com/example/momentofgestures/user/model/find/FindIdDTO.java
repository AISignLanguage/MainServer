package com.example.momentofgestures.user.model.find;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdDTO {

    private String name;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    private String phone_number;

    public FindIdDTO(String name, String phone_number) {
        this.name = name;
        this.phone_number = phone_number;
    }
}
