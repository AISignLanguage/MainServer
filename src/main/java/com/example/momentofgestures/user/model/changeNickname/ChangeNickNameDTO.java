package com.example.momentofgestures.user.model.changeNickname;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeNickNameDTO {

    private String originalNickname;

    @Size(min = 2, max = 6)
    private String nickname;

    public ChangeNickNameDTO(String originalNickname, String nickname) {
        this.originalNickname = originalNickname;
        this.nickname = nickname;
    }

    public ChangeNickNameDTO() {}
}
