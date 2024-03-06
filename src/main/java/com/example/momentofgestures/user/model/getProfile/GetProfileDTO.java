package com.example.momentofgestures.user.model.getProfile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
public class GetProfileDTO {
    private String url;
    private String name;
    private String nickName;
    private String password;
    private String birthdate;
    private String phone_number;

    public GetProfileDTO(String url, String name, String nickName,
                         String password, String birthdate, String phone_number) {
        this.url = url;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.birthdate = birthdate;
        this.phone_number = phone_number;
    }
}
