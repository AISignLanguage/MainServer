package com.example.momentofgestures.user.controller;

import com.example.momentofgestures.user.db.UserRepository;
import com.example.momentofgestures.user.model.*;
import com.example.momentofgestures.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mog/user")
@RequiredArgsConstructor //2
public class UserApiController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/find-all")
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("pp")
    public UserEntity create(
            @Valid
            @RequestBody
            UserRegisterRequest userRegisterRequest
    ) {
        return userService.create(userRegisterRequest);
    }

    @PostMapping("/send-callList")
    public CallListResponse sendCallListData(@RequestBody CallList user) {
        CallListResponse response = new CallListResponse();
        response.setUri("hello");
        response.setInstallCheck(true);
        return response;
    }

    @PostMapping("/send-data")
    public MyResponse sendData(@RequestBody User user) {
        MyResponse response = new MyResponse();
        response.setId_ok(true);
        response.setPwd_ok(true);
        return response;
    }

}

/*
@PostMapping("")
    public Api<UserRegisterRequest> register(
            @Valid
            @RequestBody
            Api<UserRegisterRequest> userRegisterRequest
    ) {

        log.info("init : {}", userRegisterRequest);

        var body = userRegisterRequest.getData();

        Api<UserRegisterRequest> response = Api.<UserRegisterRequest> builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(body)
                .build();

        return response;
    }

 */