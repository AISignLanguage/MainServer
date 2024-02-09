package com.example.momentofgestures.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallListResponse {

    public CallListResponse() {}

    public CallListResponse(String uri, Boolean installCheck) {
        this.uri = uri;
        this.installCheck = installCheck;
    }

    private String uri;
    private Boolean installCheck;
}


