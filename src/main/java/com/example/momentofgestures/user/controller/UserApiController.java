package com.example.momentofgestures.user.controller;

import com.example.momentofgestures.user.db.UserRepository;
import com.example.momentofgestures.user.model.*;
import com.example.momentofgestures.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
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

    @PostMapping("/send-callList")
    public PhoneListDTO sendCallListData(@Valid @RequestBody PhoneNumber number) {
        // 안드로이드에서 받은 전화번호 리스트
        List<String> phoneNumbers = number.getPhoneNumber();

        // 1. DB에 있는 전화번호만 뽑아서 리스트 생성
        List<UserEntity> allUsers = userRepository.findAll();
        List<String> dbPhoneNumbers = allUsers.stream() //phoneNumber 추출해서 리스트에 저장
                .map(UserEntity::getPhoneNumber) //UserEntity -> phoneNumber 변환
                .collect(Collectors.toList());
        System.out.println("Received phone numb ers: " + dbPhoneNumbers);

        // 2. DB에 있는 번호와 안드로이드에서 받은 번호 비교해서 일치하는 번호 리스트 생성
        List<String> matchingNumbers = new ArrayList<>();
        for (String phoneNumber : phoneNumbers)
            if (dbPhoneNumbers.contains(phoneNumber))
                matchingNumbers.add(phoneNumber);

        // 3. DB에 있는 번호에 해당하는 이름과 Uri 가져와서 하나의 리스트로 묶고 리스트로 한번 더 묶음
        // -> 각 사용자의 이름, 번호, Uri 리스트가 담긴 리스트 반환.
        PhoneListDTO matchingContactsList = new PhoneListDTO();
        List<List<PhoneDTO>> phones = new ArrayList<>();

        for (String phoneNumber : matchingNumbers) {
            List<PhoneDTO> matchingContacts = new ArrayList<>();
            for (UserEntity user : allUsers) {
                if (user.getPhoneNumber().equals(phoneNumber)) {
                    matchingContacts.add(new PhoneDTO(user.getName(), user.getPhoneNumber(), user.getProfileImageUrl()));
                }
            }
            phones.add(matchingContacts);
        }
        matchingContactsList.setPhones(phones);
        return matchingContactsList;
    }



    @PostMapping("/confirm-email")
    public ResponseEntity<ConfirmedDTO> confirmEmail(@RequestBody ConfirmDTO confirmDTO){
        boolean success = userService.confirmEmail(confirmDTO.getWord());
        ConfirmedDTO response = new ConfirmedDTO(success);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/confirm-nick")
    public ResponseEntity<ConfirmedDTO> confirmNickname(@RequestBody ConfirmDTO confirmDTO){
        boolean success = userService.confirmNickname(confirmDTO.getWord());
        ConfirmedDTO response = new ConfirmedDTO(success);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean success = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        LoginResponseDTO response = new LoginResponseDTO(success);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/send-data")
    public UserEntity sendData(@Valid @RequestBody UserRegisterRequest user) {
        return userService.create(user);
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