package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.LikeRequestDto;
import com.sparta.foodrecipe.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    // 좋아요 갯수 업데이트
    @PostMapping("/api/like/{postId}")
    @ResponseBody
    public Long updateLike(@RequestBody LikeRequestDto requestDto, @PathVariable Long postId) {

        return likeService.update(requestDto, postId);
    }
}
