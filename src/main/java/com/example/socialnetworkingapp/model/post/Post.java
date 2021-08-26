package com.example.socialnetworkingapp.model.post;

import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="posts")
public class Post implements Serializable {

    public Post(String title, String payload, Account author) {
        this.title = title;
        this.payload = payload;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    //title and text of post
    private String title;
    private String payload;

    //author of post
    //1 post -> 1 author
    //1 author -> many posts
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account author;

    //date of creation
    private Date date ;

    //path to photo / video
    private String filePath;

    public Post(String title, String payload, Account author, Date date, String filePath) {
        this.title = title;
        this.payload = payload;
        this.author = author;
        this.date = date;
        this.filePath = filePath;
    }
}
