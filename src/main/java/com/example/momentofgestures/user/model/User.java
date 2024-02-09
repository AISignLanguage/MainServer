package com.example.momentofgestures.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class User {
    private String name;
    private String birthday;
    private String email;
    private String password;
    private String nickName;
    private String phoneNumber;

    public User() {
    }

    public User(String name, String birthday, String email, String password, String nickName, String phoneNumber) {
        this.name = name;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }

}



