package com.example.momentofgestures.user.model.changeNickname;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNickNameResultDTO {
    private int result;

    public ChangeNickNameResultDTO(int result) {
        this.result = result;
    }
}
