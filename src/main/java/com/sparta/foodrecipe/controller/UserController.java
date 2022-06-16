package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.LoginRequestDto;
import com.sparta.foodrecipe.dto.LoginResponseDto;
import com.sparta.foodrecipe.dto.SignupRequestDto;
import com.sparta.foodrecipe.dto.SignupResponseDto;
import com.sparta.foodrecipe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/user/signup")
    public SignupResponseDto registerUser(@RequestBody SignupRequestDto requestDto) throws NoSuchAlgorithmException {

        return userService.registerUser(requestDto);
    }

    // 로그인
    @PostMapping("/user/login")
    public LoginResponseDto loginUser(@RequestBody LoginRequestDto requestDto) throws NoSuchAlgorithmException {
        return userService.loginUser(requestDto);
    }
}
