package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.CommentRequestDto;
import com.sparta.foodrecipe.dto.CommentResponseDto;
import com.sparta.foodrecipe.model.Comment;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.model.TokenDecode;
import com.sparta.foodrecipe.model.User;
import com.sparta.foodrecipe.repository.CommentRepository;
import com.sparta.foodrecipe.repository.PostRepository;
import com.sparta.foodrecipe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 댓글 작성하기
    public void postComment(CommentRequestDto commentRequestDto, TokenDecode tokenDecode){

        User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
                () -> new NullPointerException("해당하는 아이디가 없습니다."));
//        User user = userRepository.findByUsername(commentRequestDto.getUsername());
        Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(()
                -> new NullPointerException("포스트 아이디를 찾을 수 없습니다."));


        //Comment comment = new Comment(commentRequestDto, user);
        Comment comment  = new Comment(commentRequestDto, user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    // 댓글 조회하기
    public List<CommentResponseDto> getComment(Long postId){
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for(Comment comment : commentList){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;
    }
}