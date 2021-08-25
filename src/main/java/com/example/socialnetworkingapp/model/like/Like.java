package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="account_id", nullable = false)
    private Account user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="post_id", nullable = false)
    private Post post;
}