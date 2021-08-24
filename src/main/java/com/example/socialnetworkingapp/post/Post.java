package com.example.socialnetworkingapp.post;

import com.example.socialnetworkingapp.account.Account;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name="posts")
public class Post implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    private String title;
    private String payload;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account author;

}
