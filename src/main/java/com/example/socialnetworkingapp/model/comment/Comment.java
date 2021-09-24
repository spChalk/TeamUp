package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.post.Post;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String payload;

    //comments of post
    //1 comment-> 1 commenter
    //1 commenter -> many comments
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account commenter;

    //comments of post
    //1 comment-> 1 post
    //1 post ->many comments
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="post_id", nullable = false)
    private Post post;

    //date of creation
    private String date;

    public Comment(String payload, Account commenter, Post post, String date) {
        this.payload = payload;
        this.commenter = commenter;
        this.post = post;
        this.date = date;
    }
}