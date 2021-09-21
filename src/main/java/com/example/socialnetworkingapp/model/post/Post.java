package com.example.socialnetworkingapp.model.post;

import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="posts")
public class Post implements Serializable {

    public Post(String payload, Account author) {
        this.payload = payload;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @Lob
    private String payload;

    //author of post
    //1 post -> 1 author
    //1 author -> many posts
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account author;

    //date of creation
    private Date date ;

    private String imagePath;
    private String videoPath;
    private String soundPath;

    public Post(String payload, Account author, Date date, String imagePath, String videoPath, String soundPath) {
        this.payload = payload;
        this.author = author;
        this.date = date;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
        this.soundPath = soundPath;
    }
}
