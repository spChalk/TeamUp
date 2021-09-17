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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @NotNull
    private String school;

    private String degree;

    private String field;

    private String startDate;

    private String endDate;

    private float grade;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @NotNull
    private Boolean visible = true;
}