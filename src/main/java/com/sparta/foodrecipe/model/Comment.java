package com.sparta.foodrecipe.model;

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

//    @ManyToOne
//    @JoinColumn(name="USER_ID")
//    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String content;

//    @ManyToOne
//    @JoinColumn(name="POST_ID")
//    private Post post;

    @Column(nullable = false)
    private Long postId;


//    public void setPost(Post post) {
//        this.post = post;
//        post.getComments().add(this);
//    }

}
