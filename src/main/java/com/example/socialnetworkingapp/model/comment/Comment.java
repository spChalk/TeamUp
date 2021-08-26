package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.post.Post;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @NotNull
    private String payload;

    //comments of post
    //1 comment-> 1 commenter
    //1 commenter -> many comments
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="account_id", nullable = false)
    private Account commenter;

    //comments of post
    //1 comment-> 1 post
    //1 post ->many comments
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="post_id", nullable = false)
    private Post post;

    //date of creation
    private Date date;

    public Comment(String payload, Account commenter, Post post, Date date) {
        this.payload = payload;
        this.commenter = commenter;
        this.post = post;
        this.date = date;
    }
}