package com.sparta.foodrecipe.repository;

import com.sparta.foodrecipe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    boolean existsByUsernameAndPassword(String username, String encryptedPassword);
}