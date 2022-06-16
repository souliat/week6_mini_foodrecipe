package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.PostRequestDto;
import com.sparta.foodrecipe.dto.PostResponseDto;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.model.TokenDecode;
import com.sparta.foodrecipe.model.User;
import com.sparta.foodrecipe.repository.LikeRepository;
import com.sparta.foodrecipe.repository.PostRepository;
import com.sparta.foodrecipe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {


    private final S3Service s3service;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository; // LikeCount, LikeByMe 가져와야함.
    private final UserRepository userRepository;

    // 새로운 글 작성
    public void postSave(PostRequestDto postRequestDto, MultipartFile multipartFile, String dirName, TokenDecode tokenDecode){

//        Map<String, String> map = s3service.upload(multipartFile, dirName);
        String imageUrl = s3service.upload(multipartFile, dirName);
        User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
                () -> new NullPointerException("해당하는 아이디가 없습니다"));

//        String username = "test";
//        String nickname = "test";

//        Post post = new Post(postRequestDto, username, nickname, map.get("imageUrl"), map.get("fileName"));
        Post post = new Post(postRequestDto, user.getUsername(), user.getNickname(), imageUrl);

        postRepository.save(post);
    }

//    public void postSave(PostRequestDto postRequestDto, MultipartFile multipartFile, String dirName){
//
////        Map<String, String> map = s3service.upload(multipartFile, dirName);
//        String imageUrl = s3service.upload(multipartFile, dirName);
////        User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
////                () -> new NullPointerException("해당하는 아이디가 없습니다"));
//
//        String username = "test";
//        String nickname = "test";
//
////        Post post = new Post(postRequestDto, username, nickname, map.get("imageUrl"), map.get("fileName"));
//        Post post = new Post(postRequestDto, username, nickname, imageUrl);
//
//        postRepository.save(post);
//    }

    @Transactional
    // 게시글 수정. postRequestDto(title, content)
    public Post postUpdate(PostRequestDto postRequestDto,Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 없습니다.")
        );
//        System.out.println("이미지 유알엘 : " + post.getImageUrl());
        String username = "test";
        String nickname = "test";

        post.update(postRequestDto, username, nickname);

        return post;
    }

    // 전체 글 조회
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto responseDto = new PostResponseDto(post);

            Long likeCount = likeRepository.countByPostId(post.getId());
            boolean likeByMe = likeRepository.existsByUsernameAndPostId(post.getUsername(), post.getId());

            responseDto.setLikeCount(likeCount);
            responseDto.setLikeByMe(likeByMe);

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    // 특정 카테고리 글 조회
    public List<PostResponseDto> getCategorizedPosts(String categoryId) {
        List<Post> posts = postRepository.findAllByCategoryIdOrderByCreatedAtDesc(categoryId);
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto responseDto = new PostResponseDto(post);

            Long likeCount = likeRepository.countByPostId(post.getId());
            boolean likeByMe = likeRepository.existsByUsernameAndPostId(post.getUsername(), post.getId());

            responseDto.setLikeCount(likeCount);
            responseDto.setLikeByMe(likeByMe);

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    // 상세 페이지 글 조회
    public PostResponseDto getDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 아이디는 없습니다."));

        PostResponseDto responseDto = new PostResponseDto(post);
        Long likeCount = likeRepository.countByPostId(post.getId());
        boolean likeByMe = likeRepository.existsByUsernameAndPostId(post.getUsername(), post.getId());

        responseDto.setLikeCount(likeCount);
        responseDto.setLikeByMe(likeByMe);

        return responseDto;
    }

    //게시글 삭제
    public Long deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("삭제할 게시글이 없습니다."));

        postRepository.deleteById(postId);
        return postId;
    }
}
