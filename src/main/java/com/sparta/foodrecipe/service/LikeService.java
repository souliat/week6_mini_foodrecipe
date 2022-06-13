package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.LikeRequestDto;
import com.sparta.foodrecipe.model.Like;
import com.sparta.foodrecipe.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    @Transactional
    public Long update(LikeRequestDto likeRequestDto, Long postId) {

            
            Like like = new Like(likeRequestDto, postId);

            if (likeRequestDto.getAction().equals("like")) {
                likeRepository.save(like);
            }else {
                likeRepository.deleteByPostId(postId);
            }

            return likeRepository.countByPostId(postId);
    }
}
