package com.example.socialnetworkingapp.model.tags;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="tags")
public class Tag {

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 52)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @NotNull
    private String tag;

    public Tag(String tag) {
        this.tag = tag;
    }
}
