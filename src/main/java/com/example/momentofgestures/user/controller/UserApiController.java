package com.example.momentofgestures.user.controller;

import com.example.momentofgestures.user.db.UserRepository;
import com.example.momentofgestures.user.model.*;
import com.example.momentofgestures.user.model.call.PhoneDTO;
import com.example.momentofgestures.user.model.call.PhoneListDTO;
import com.example.momentofgestures.user.model.call.PhoneNumber;
import com.example.momentofgestures.user.model.changeNickname.ChangeNickNameDTO;
import com.example.momentofgestures.user.model.changeNickname.ChangeNickNameResultDTO;
import com.example.momentofgestures.user.model.find.FindIdDTO;
import com.example.momentofgestures.user.model.find.FindPwdDTO;
import com.example.momentofgestures.user.model.find.FindPwdOk;
import com.example.momentofgestures.user.model.find.GetIdDTO;
import com.example.momentofgestures.user.model.getProfile.GetProfileDTO;
import com.example.momentofgestures.user.model.getProfile.ProfileRequestDTO;
import com.example.momentofgestures.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<GetIdDTO> sendId(@Valid @RequestBody FindIdDTO findIdDTO) {
        String name = findIdDTO.getName();
        String phoneNumber = findIdDTO.getPhone_number();
        System.out.println("name: " + name + ", phoneNumber: " +phoneNumber);

        GetIdDTO getIdDTO = new GetIdDTO(userService.findId(name, phoneNumber));

        return ResponseEntity.ok(getIdDTO);
    }

    // 비밀번호 찾기
    @PostMapping("/find-pwd")
    public ResponseEntity<FindPwdOk> sendPwd(@Valid @RequestBody FindPwdDTO findPwdDTO) {
        String name = findPwdDTO.getName();
        String email = findPwdDTO.getEmail();
        System.out.println("name: " + name + " / email: " +email);

        FindPwdOk findPwdOk = new FindPwdOk(userService.findPwd(name, email));

        return ResponseEntity.ok(findPwdOk);
    }

    // 클라이언트에서 보낸 번호에 해당하는 사용자 정보 반환
    @PostMapping("/send-callList")
    public ResponseEntity<PhoneListDTO> sendCallListData(@Valid @RequestBody PhoneNumber number) {
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
        // -> 각 사용자의 이름, 번호, Uri 리스트가 담긴 리스트 반환........
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
        return ResponseEntity.ok(matchingContactsList);
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

    // 비밀번호 찾기 (조회)
    @PostMapping("/checkPassword")
    public ResponseEntity<CheckPasswordResponseDTO> checkPassword(@RequestBody CheckPasswordRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        String password = userService.getPasswordByEmail(email);

        if (password != null) {
            // 이메일에 해당하는 비밀번호가 데이터베이스에서 조회되었을 경우
            CheckPasswordResponseDTO responseDTO = new CheckPasswordResponseDTO(password);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            // 이메일에 해당하는 사용자가 없거나 비밀번호가 없는 경우
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/changePassword")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@RequestBody ChangePasswordRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        String newPassword = requestDTO.getNewPassword();

        boolean success = userService.changePasswordDatabase(email, newPassword);

        if (success) {
            ChangePasswordResponseDTO responseDTO = new ChangePasswordResponseDTO(true);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ChangePasswordResponseDTO responseDTO = new ChangePasswordResponseDTO(false);
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    //유저 정보 삭제
    @PostMapping("/deleteUser")
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(@RequestBody DeleteUserRequestDTO requestDTO) {
        String email = requestDTO.getEmail();

        boolean success = userService.deleteUserByEmail(email);

        if (success) {
            return ResponseEntity.ok(new DeleteUserResponseDTO(true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteUserResponseDTO(false));
        }
    }

    @PostMapping("/send-data")
    public UserEntity sendData(@Valid @RequestBody UserRegisterRequest user) {
        return userService.create(user);
    }


    // 사용자 정보 요청 - 응답
    @PostMapping("/requestProfile")
    public ResponseEntity<GetProfileDTO> requestProfile(@Valid @RequestBody ProfileRequestDTO profileRequestDTO)
    {
       Long id = userService.getIdByEmail(profileRequestDTO.getEmail()); //받은 이메일에 해당하는 DB의 ID 검색
       List<UserEntity> allUsers = userRepository.findAll();

        GetProfileDTO getProfileDTO = null;

       for(UserEntity user : allUsers) {
           if (user.getId().equals(id)) {
                getProfileDTO = new GetProfileDTO(user.getProfileImageUrl(), user.getName(), user.getNickname(),
                       user.getPassword(), user.getBirthdate().toString(), user.getPhoneNumber());
                break;
           }
       }
        return ResponseEntity.ok(getProfileDTO);
    }

    // 닉네임 변경
    @PostMapping("/changeNickName")
    public ResponseEntity<ChangeNickNameResultDTO> changeNickName
            (@Valid @RequestBody ChangeNickNameDTO changeNickNameDTO) {

        ChangeNickNameResultDTO result = null;
        String originalNickname = changeNickNameDTO.getOriginalNickname(); // 원본 닉네임
        String nickName = changeNickNameDTO.getNickname(); // 바꿀 닉네임
        List<UserEntity> allUsers = userRepository.findAll();
        System.out.println("origin: " + originalNickname + " nickname: " + nickName);

        // 바꿀 닉네임이 null인 경우
        if (nickName.isBlank()) {
            result = new ChangeNickNameResultDTO(1); // 바꿀 닉네임이 없습니다..
            return ResponseEntity.ok(result);
        }

        // 1. DB 전체 닉네임 중 바꿀 닉네임이 존재 여부 확인
        for(UserEntity user : allUsers) {
            // 2. 닉네임이 존재 하는 경우 -> 이미 존재하는 닉네임입니다.
            if (user.getNickname().equals(nickName)) {
                result = new ChangeNickNameResultDTO(2); // 이미 존재하는 닉네임..
                break;
            }
            // 3. 닉네임이 존재하지 않는 경우 -> 원본 닉네임 변경 (id 이용 -> 해당 항목 닉네임 업데이트)
            else {
                userService.changeNickNameDatabase(originalNickname, nickName);
                result = new ChangeNickNameResultDTO(3);
                break;
            }
        }
        return ResponseEntity.ok(result);
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