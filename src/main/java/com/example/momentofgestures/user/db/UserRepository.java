package com.example.momentofgestures.user.db;

import com.example.momentofgestures.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailAndPassword(String email, String password);
    UserEntity findByEmail(String email);
    UserEntity findByNickname(String nick);

}