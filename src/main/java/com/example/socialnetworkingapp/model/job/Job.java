package com.example.socialnetworkingapp.model.job;

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
@Table(name = "jobs")
public class Job implements Serializable {

    private enum JobType {
        FULL_TIME,
        PART_TIME,
        INTERNSHIP,
        CONTRACT,
        OTHER
    }

    private enum ExperienceLevel {
        INTERNSHIP,
        ENTRY_LEVEL,
        ASSOCIATE,
        MIN_SENIOR,
        DIRECTOR,
        EXECUTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String company;

    @NotNull
    private String location;

    private Date datePosted;

    @NotNull
    private JobType jobType;

    @NotNull
    private ExperienceLevel experienceLevel;

    @NotNull
    private String info;
}