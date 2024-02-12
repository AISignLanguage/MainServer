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

}