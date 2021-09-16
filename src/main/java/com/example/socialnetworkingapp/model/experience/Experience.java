package com.example.socialnetworkingapp.model.experience;

import com.example.socialnetworkingapp.model.account.Account;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="experience")
public class Experience implements Serializable {

    private enum EmploymentType {
        FULL_TIME,
        PART_TIME,
        SELF_EMPLOYED,
        FREELANCE,
        CONTRACT,
        INTERNSHIP,
        APPRENTICESHIP,
        SEASONAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @NotNull
    private String title;

    @NotNull
    private EmploymentType employmentType;

    @NotNull
    private String company;

    private String location;

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    private String headline;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;
}