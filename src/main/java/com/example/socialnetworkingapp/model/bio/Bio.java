package com.example.socialnetworkingapp.model.bio;
import com.example.socialnetworkingapp.model.account.Account;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

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
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    public Bio(String description) {
        this.description = description;
    }
}
