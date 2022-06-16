package com.sparta.foodrecipe.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sparta.foodrecipe.dto.LoginRequestDto;
import com.sparta.foodrecipe.dto.LoginResponseDto;
import com.sparta.foodrecipe.dto.SignupRequestDto;
import com.sparta.foodrecipe.dto.SignupResponseDto;
import com.sparta.foodrecipe.model.User;
import com.sparta.foodrecipe.repository.UserRepository;
import com.sparta.foodrecipe.security.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    public SignupResponseDto registerUser(SignupRequestDto requestDto) throws NoSuchAlgorithmException {

        boolean result = true;
        String errorMsg = "회원가입을 성공하였습니다.";
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();

        boolean isExistUser = userRepository.existsByUsername(username);
        boolean isExistNickname = userRepository.existsByNickname(nickname);

        if(isExistUser) {
            errorMsg = "중복된 사용자 아이디가 존재합니다.";
            result = false;
            return new SignupResponseDto(result, errorMsg);
        }
        if(isExistNickname) {
            errorMsg = "중복된 닉네임이 존재합니다.";
            result = false;
            return new SignupResponseDto(result, errorMsg);
        }

        String password = SHA256.encrypt(requestDto.getPassword());

        User user = new User(username, password, nickname);
        userRepository.save(user);

        return new SignupResponseDto(result, errorMsg);
    }

    // 로그인
    public LoginResponseDto loginUser(LoginRequestDto requestDto) throws NoSuchAlgorithmException{
        boolean result = true;
        String errorMsg = "로그인 성공!";
        String token = "";

        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String encryptedPassword = SHA256.encrypt(password);

        boolean isExistUser = userRepository.existsByUsernameAndPassword(username, encryptedPassword);

        if (!isExistUser) {
            result = false;
            errorMsg = "아이디 또는 비밀번호가 틀렸습니다.";
            return new LoginResponseDto(result, errorMsg);
        }

        try {
            User user = userRepository.findByUsername(username);
            Algorithm algorithm = Algorithm.HMAC256("zheldWhffkwkfgkrhtlvek");

            token = JWT.create()
                    .withIssuer("holyshit")
                    .withClaim("id", user.getId())
                    .withClaim("username", user.getUsername())
                    .withClaim("nickname", user.getNickname())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            result = false;
            errorMsg = "토큰 생성에 실패하였습니다.";
            return new LoginResponseDto(result, errorMsg);
        }

        return new LoginResponseDto(result, errorMsg, token, username);
    }
}
