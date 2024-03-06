package com.example.momentofgestures.user.model.changeNickname;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNickNameResultDTO {
    private Boolean success;

    public ChangeNickNameResultDTO(Boolean success) {
        this.success = success;
    }
}
