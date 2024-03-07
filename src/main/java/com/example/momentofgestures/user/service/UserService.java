package com.example.momentofgestures.user.service;

import com.example.momentofgestures.user.db.UserRepository;
import com.example.momentofgestures.user.model.UserEntity;
import com.example.momentofgestures.user.model.UserRegisterRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;

/*
{
  "name" : "신짱구",
  "birthdate" : "2019-05-05",
  "email" : "shinjjang@naver.com",
  "password" : "chocobi0505",
  "nickname" : "짱구는못말려",
  "phone_number" : "010-1234-1234",
  "profile_image_url" : "https://cdn-icons-png.flaticon.com/128/149/149071.png"
}
*/


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;



    public UserEntity create(UserRegisterRequest userRegisterRequest) {
        var entity = UserEntity.builder()
                .name(userRegisterRequest.getName())
                .nickname(userRegisterRequest.getNickname())
                .birthdate(userRegisterRequest.getBirthdate())
                .email(userRegisterRequest.getEmail())
                .password(userRegisterRequest.getPassword())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .profileImageUrl(userRegisterRequest.getProfileImageUrl())
                .registerdAt(LocalDateTime.now())
                .build();
        return userRepository.save(entity);
    }


    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void delete(UserEntity id){
        userRepository.delete(id);
    }

    public Optional<UserEntity> findById(Long id){
        return userRepository.findById(id);
    }
    public UserEntity updateUser(Long id, UserRegisterRequest updateRequest) {
        // 사용자 ID로 기존 사용자 정보를 조회 d
        Optional<UserEntity> existingUser = findById(id);
        if (existingUser.isPresent()) {
            UserEntity userEntity = existingUser.get();
            userEntity.setName(updateRequest.getName());
            userEntity.setNickname(updateRequest.getNickname());
            userEntity.setBirthdate(updateRequest.getBirthdate());
            userEntity.setEmail(updateRequest.getEmail());
            userEntity.setPassword(updateRequest.getPassword());
            userEntity.setPhoneNumber(updateRequest.getPhoneNumber());
            userEntity.setProfileImageUrl(updateRequest.getProfileImageUrl());
            // 등록된 날짜는 업데이트하지 않음
            // 업데이트된 사용자 정보를 저장
            return userRepository.save(userEntity);
        } else {
            // 해당 ID의 사용자가 존재하지 않는 경우 예외 처리 또는 다른 로직 구현
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // email로 id 반환
    public Long getIdByEmail(String email) {
        UserEntity userEmail = userRepository.findByEmail(email);
        return userEmail.getId();
    }

    // 사용자 이메일 존재 확인하고 비밀번호가 일치하면 로그인 성공
    public boolean login(String email, String password) {
        UserEntity user = userRepository.findByEmailAndPassword(email, password);
        return user != null;
    }

    //이메일에 해당하는 비밀번호 찾기
    public String getPasswordByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getPassword();
        } else {
            return null;
        }
    }

    //비밀번호 변경
    public boolean changePasswordDatabase(String email, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }

        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    //유저 정보 삭제
    public boolean deleteUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);

        if (user != null) {
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }

    // 아이디 찾기
    public String findId(String name, String phoneNumber) {
        UserEntity userName = userRepository.findByName(name);
        UserEntity userPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);

        //이름이 db에 있고 이름에 해당하는 번호, 번호에 해당하는 이름이 일치하면
        if (userName != null && userPhoneNumber != null) {
            if(userName.getId().equals(userPhoneNumber.getId())) {
                System.out.println(userPhoneNumber.getEmail());
                return userPhoneNumber.getEmail();
            }
        }
        return null;
    }

    // 비밀번호 찾기
    public Boolean findPwd(String name, String email) {
        UserEntity userName = userRepository.findByName(name);
        UserEntity userEmail = userRepository.findByEmail(email);

        if (userName != null && userEmail != null) {
            if (userName.getId().equals(userEmail.getId())){
                return true;
            }
        }
        return false;
    }

    public boolean confirmEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return user != null;
    }
    public boolean confirmNickname(String nick) {
        UserEntity user = userRepository.findByNickname(nick);
        return user != null;
    }
}