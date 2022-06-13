package com.sparta.foodrecipe.model;

import com.sparta.foodrecipe.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped {
    // ID
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

//    @ManyToOne
//    @JoinColumn(name="USER_ID")
//    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = true)
    private Long likeCount;

    @Column(nullable = true)
    boolean likeByMe;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "post")
//    private List<Like> likes = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, String username, String nickname, String imageUrl) {
        this.username = username;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.categoryId = postRequestDto.getCategoryId();;
    }

}
