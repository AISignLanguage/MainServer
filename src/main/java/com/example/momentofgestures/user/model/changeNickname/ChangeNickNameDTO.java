package com.example.momentofgestures.user.model.changeNickname;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNickNameDTO {
    private String nickname;

    public ChangeNickNameDTO(String nickname) {
        this.nickname = nickname;
    }

    public ChangeNickNameDTO() {}
}
