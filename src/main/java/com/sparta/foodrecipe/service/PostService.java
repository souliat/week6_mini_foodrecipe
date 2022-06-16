package com.sparta.foodrecipe.service;

import com.sparta.foodrecipe.dto.PostRequestDto;
import com.sparta.foodrecipe.dto.PostResponseDto;
import com.sparta.foodrecipe.model.Comment;
import com.sparta.foodrecipe.model.Post;
import com.sparta.foodrecipe.model.TokenDecode;
import com.sparta.foodrecipe.model.User;
import com.sparta.foodrecipe.repository.CommentRepository;
import com.sparta.foodrecipe.repository.LikeRepository;
import com.sparta.foodrecipe.repository.PostRepository;
import com.sparta.foodrecipe.repository.UserRepository;
import jdk.nashorn.internal.parser.Token;
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
    private final CommentRepository commentRepository;

    // 새로운 글 작성
    public void postSave(PostRequestDto postRequestDto, MultipartFile multipartFile, String dirName, TokenDecode tokenDecode){

//        Map<String, String> map = s3service.upload(multipartFile, dirName);
        String imageUrl = s3service.upload(multipartFile, dirName);
        User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
                () -> new NullPointerException("해당하는 아이디가 없습니다"));

//        Post post = new Post(postRequestDto, username, nickname, map.get("imageUrl"), map.get("fileName"));
        Post post = new Post(postRequestDto, user.getUsername(), user.getNickname(), imageUrl);

        postRepository.save(post);
    }

    // 전체 글 조회
    public List<PostResponseDto> getPosts(TokenDecode tokenDecode) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        if (tokenDecode != null) {
            for (Post post : posts) {
                PostResponseDto responseDto = new PostResponseDto(post);

                String username = tokenDecode.getUsername();
                String username2 = username.substring(1, username.length()-1);

                Long likeCount = likeRepository.countByPostId(post.getId());
                boolean likeByMe = likeRepository.existsByUsernameAndPostId(username2, post.getId());

                responseDto.setLikeCount(likeCount);
                responseDto.setLikeByMe(likeByMe);

                responseDtoList.add(responseDto);
            }
        } else {
            for (Post post : posts) {
                PostResponseDto responseDto = new PostResponseDto(post);

                Long likeCount = likeRepository.countByPostId(post.getId());

                responseDto.setLikeCount(likeCount);

                responseDtoList.add(responseDto);
            }
        }
        return responseDtoList;
    }

    @Transactional
    // 게시글 수정 postRequestDto(title, content)
    public Post postUpdate(PostRequestDto postRequestDto, Long postId, TokenDecode tokenDecode) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 없습니다.")
        );

        User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
                () -> new NullPointerException("해당하는 아이디가 없습니다"));

        if(!post.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("글쓴이만 수정이 가능합니다.");
        }

        post.update(postRequestDto, user.getUsername(), user.getNickname());

        return post;
    }

    @Transactional
    //게시글 삭제 ( 댓글이 있는 게시글의 경우 삭제할때 500에러가 뜬다.)
    public Long deletePost(Long postId, TokenDecode tokenDecode) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("삭제할 게시글이 없습니다."));

        User user = userRepository.findById(tokenDecode.getId()).orElseThrow(
                () -> new NullPointerException("해당하는 아이디가 없습니다"));

        if(!post.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("글쓴이만 삭제가 가능합니다.");
        }

        commentRepository.deleteAllByPostId(postId);
        postRepository.deleteById(post.getId());
        return postId;
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
    public PostResponseDto getDetail(Long postId, TokenDecode tokenDecode) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 아이디는 없습니다."));

        PostResponseDto responseDto = new PostResponseDto(post);

        if (tokenDecode != null) {
            Long likeCount = likeRepository.countByPostId(post.getId());
            String username = tokenDecode.getUsername();
            String username2 = username.substring(1, username.length()-1);

            boolean likeByMe = likeRepository.existsByUsernameAndPostId(username2, post.getId());

            responseDto.setLikeCount(likeCount);
            responseDto.setLikeByMe(likeByMe);
        } else {
            Long likeCount = likeRepository.countByPostId(post.getId());

            responseDto.setLikeCount(likeCount);
        }
        return responseDto;
    }
}
