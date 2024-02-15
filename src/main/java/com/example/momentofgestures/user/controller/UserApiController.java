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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/create-user")
    public UserEntity create(
            @Valid
            @RequestBody
            UserRegisterRequest userRegisterRequest
    ) {
        return userService.create(userRegisterRequest);
    }

    @PostMapping("/send-callList")
    public CallListResponse sendCallListData(@RequestBody PhoneNumber number) {
        //List<String> phoneNumbers = number.getPhoneNumbers();
        System.out.println("Received phone numbers: " + number.getPhoneNumber());

        CallListResponse response = new CallListResponse();
        response.setUri("hello");
        response.setInstallCheck(true);
        return response;
    }

    @PostMapping("/send-data")
    public UserEntity sendData(@Valid @RequestBody UserRegisterRequest user) {
        return userService.create(user);
    }

    @GetMapping("/get-callList")
    public List<String> getAllPhoneNumbers() {
        List<UserEntity> allUsers = userRepository.findAll(); //DB에서 모든 사용자 정보 가져옴
        // allUser에서 phoneNumber만 추출해서 리스트에 저장
        List<String> phoneNumbers = allUsers.stream()
                .map(UserEntity::getPhoneNumber) //UserEntity를 phoneNumber로 변환
                .collect(Collectors.toList());
        return phoneNumbers;
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