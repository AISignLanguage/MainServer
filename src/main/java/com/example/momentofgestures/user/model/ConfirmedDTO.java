package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmedDTO {

    private boolean ok;

    public ConfirmedDTO() {
    }

    public ConfirmedDTO(boolean ok) {
        this.ok = ok;
    }
}
