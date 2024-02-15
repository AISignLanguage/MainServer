package com.example.momentofgestures.user.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRegisterRequest {

    @NotBlank
    private String name;


    @NotNull
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    @NotBlank
    @Size(min = 2, max = 6)
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    private String phoneNumber;

    @URL
    @NotBlank
    private String profileImageUrl;


    @AssertTrue(message = "이름과 닉네임은 반드시 존재해야 합니다.")
    public boolean isNameCheck() {

        if (Objects.nonNull(name) && !name.isBlank()) {
            return true;
        }

        if (Objects.nonNull(nickname) && !nickname.isBlank()) {
            return true;
        }

        return false;
    }

}
