package com.example.socialnetworkingapp.model.bio;

import lombok.*;
import org.hibernate.annotations.Type;

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
    @TableGenerator(name = "Id_Gen", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
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
