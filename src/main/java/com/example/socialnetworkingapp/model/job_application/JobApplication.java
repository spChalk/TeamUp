package com.example.socialnetworkingapp.model.job_application;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job.Job;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job_application")
public class JobApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private Account applicant;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private Job job;

    public JobApplication(Account applicant, Job job) {
        this.applicant = applicant;
        this.job = job;
    }
}