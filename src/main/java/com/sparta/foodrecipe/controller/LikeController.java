package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.LikeRequestDto;
import com.sparta.foodrecipe.model.TokenDecode;
import com.sparta.foodrecipe.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    // 좋아요 갯수 업데이트
    @PostMapping("/api/like/{postId}")
    @ResponseBody
    public Long updateLike(@RequestBody LikeRequestDto requestDto, @PathVariable Long postId, HttpServletRequest httpRequest) {

        TokenDecode tokenDecode = (TokenDecode) httpRequest.getAttribute("decode");
        return likeService.update(requestDto, postId, tokenDecode);
    }
}
