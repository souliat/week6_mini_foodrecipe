package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.LikeRequestDto;
import com.sparta.foodrecipe.model.Like;
import com.sparta.foodrecipe.model.TokenDecode;
import com.sparta.foodrecipe.model.User;
import com.sparta.foodrecipe.repository.LikeRepository;
import com.sparta.foodrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    @Transactional
    public Long update(LikeRequestDto likeRequestDto, Long postId, TokenDecode tokenDecode) {

            User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
                    () -> new NullPointerException("해당하는 유저 아이디가 없습니다."));

            Like like = new Like(user, postId);

            if (likeRequestDto.getAction().equals("like")) {
                likeRepository.save(like);
            }else {
                likeRepository.deleteByPostId(postId);
            }

            return likeRepository.countByPostId(postId);
    }
}
