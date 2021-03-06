package com.example.socialnetworkingapp.model.education;

import com.sun.istack.NotNull;
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
@Table(name="education")
public class Education implements Serializable {

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @NotNull
    private String school;

    @NotNull
    private String degree;

    @NotNull
    private String field;

    @NotNull
    private String startDate;

    private String endDate;

    private float grade;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @NotNull
    private Boolean visible = true;
}