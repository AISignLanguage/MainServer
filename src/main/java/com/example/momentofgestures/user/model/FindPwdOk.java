package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPwdOk {
    private Boolean success_find_pwd;

    public FindPwdOk(Boolean success_find_pwd) {
        this.success_find_pwd = success_find_pwd;
    }
}
