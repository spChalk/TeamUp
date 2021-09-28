package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.post.Post;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like implements Serializable {

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    private Date dateCreated;

    public Like(Account user, Post post, Date date) {
        this.user = user;
        this.post = post;
        this.dateCreated = date;
    }
}