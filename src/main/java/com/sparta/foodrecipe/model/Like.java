package com.sparta.foodrecipe.model;

import com.sparta.foodrecipe.dto.LikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Like {
    // ID
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

//    @ManyToOne
//    @JoinColumn(name="USER_ID")
//    private User user;

    @Column(nullable = false)
    private String username;

//    @ManyToOne
//    @JoinColumn(name="POST_ID")
//    private Post post;

    @Column(nullable = false)
    private Long postId;


//    public void setPost(Post post) {
//        this.post = post;
//        post.getLikes().add(this);
//    }

    public Like(LikeRequestDto likeRequestDto, Long postId) {
        this.username = likeRequestDto.getUsername();
        this.postId = postId;
    }


}
