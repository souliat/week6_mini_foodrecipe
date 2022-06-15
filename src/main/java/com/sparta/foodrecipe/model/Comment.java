package com.sparta.foodrecipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.foodrecipe.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped{
    // ID
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;
    

    public Comment(CommentRequestDto commentRequestDto, User user) {
        this.content = commentRequestDto.getContents();
        this.user = user;
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }


    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

}
