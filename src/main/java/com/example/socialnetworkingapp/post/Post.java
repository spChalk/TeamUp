package com.example.socialnetworkingapp.post;

import com.example.socialnetworkingapp.account.Account;
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

    private String title;
    private String payload;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account author;

    private Date date ;
//    private String filePath;

}
