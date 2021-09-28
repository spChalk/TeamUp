package com.example.socialnetworkingapp.model.post;

import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

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

    public Post(String payload, Account author) {
        this.payload = payload;
        this.author = author;
    }

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String payload;

    //author of post
    //1 post -> 1 author
    //1 author -> many posts
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
