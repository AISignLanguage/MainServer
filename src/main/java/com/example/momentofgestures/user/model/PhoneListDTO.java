package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhoneListDTO {
    private List<List<PhoneDTO>> phones;

}
