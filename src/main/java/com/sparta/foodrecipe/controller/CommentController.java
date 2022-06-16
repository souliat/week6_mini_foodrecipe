package com.sparta.foodrecipe.controller;

import com.sparta.foodrecipe.dto.CommentRequestDto;
import com.sparta.foodrecipe.dto.CommentResponseDto;
import com.sparta.foodrecipe.model.TokenDecode;
import com.sparta.foodrecipe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/api/comments/write")
    public void postComment(@RequestBody CommentRequestDto commentRequestDto,
                            HttpServletRequest httpRequest){
        TokenDecode tokenDecode = (TokenDecode) httpRequest.getAttribute("decode");
        commentService.postComment(commentRequestDto, tokenDecode);
    }

    // 댓글 조회
    @GetMapping("/api/comments/{postId}")
    public List<CommentResponseDto> getComment(@PathVariable Long postId) {

        return commentService.getComment(postId);
    }
}