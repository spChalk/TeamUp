package com.example.socialnetworkingapp.model.experience;

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
@Table(name="experience")
public class Experience implements Serializable {

    @Id
    @TableGenerator(name = "Id_Gen", initialValue = 101)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Gen")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @NotNull
    private String title;

    @NotNull
    private EmploymentType employmentType;

    @NotNull
    private ExperienceLevel experienceLevel;

    @NotNull
    private String company;

    private String location;

    @NotNull
    private String startDate;

    private String endDate;

    private String headline;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @NotNull
    private Boolean visible = true;
}