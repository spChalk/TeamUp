package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.post.Post;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="account_id", nullable = false)
    private Account commenter;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="post_id", nullable = false)
    private Post post;
}