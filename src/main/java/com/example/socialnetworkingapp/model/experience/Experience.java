package com.example.socialnetworkingapp.model.experience;

import com.example.socialnetworkingapp.model.account.Account;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
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
    private Date startDate;

    @NotNull
    private Date endDate;

    private String headline;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Account user;
}