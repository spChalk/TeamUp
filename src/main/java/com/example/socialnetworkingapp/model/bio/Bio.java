package com.example.socialnetworkingapp.model.bio;
import com.example.socialnetworkingapp.model.account.Account;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bio")
public class Bio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    /* Description */
    public String description;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private Account account;

    public Bio(String desc, Account account) {
        this.description = desc;
        this.account = account;
    }
}
